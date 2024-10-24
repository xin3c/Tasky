(function() {
    let swRegistration = null;

    const applicationServerPublicKey = 'BNJSYcMGYKXCl6_HSQXy43lPJkHQiHhVwaX-uczlFxPSUdw3tpBJG1C2xc1N2TxO2AZRIuj1C7QQW_j-mK8GttI';

    function urlB64ToUint8Array(base64String) {
        const padding = '='.repeat((4 - base64String.length % 4) % 4);
        const base64 = (base64String + padding)
            .replace(/\-/g, '+')
            .replace(/_/g, '/');

        const rawData = window.atob(base64);
        const outputArray = new Uint8Array(rawData.length);
        for (let i = 0; i < rawData.length; ++i) {
            outputArray[i] = rawData.charCodeAt(i);
        }
        return outputArray;
    }

    function subscribeUser() {
        const applicationServerKey = urlB64ToUint8Array(applicationServerPublicKey);
        swRegistration.pushManager.subscribe({
            userVisibleOnly: true,
            applicationServerKey: applicationServerKey
        })
        .then(function(subscription) {
            sendSubscriptionToServer(subscription);
        })
        .catch(function(error) {
            logErrorToServer(error);
        });
    }

    function askPermission() {
        Notification.requestPermission(function(status) {
            if (status === 'granted') {
                subscribeUser();
            }
        });
    }

    if ('serviceWorker' in navigator && 'PushManager' in window) {
        navigator.serviceWorker.register('/js/service-worker.js')
            .then(function(swReg) {
                swRegistration = swReg;
                askPermission();
            })
            .catch(function(error) {
                logErrorToServer(error);
            });
    } else {
        console.warn('Push-notifications are not supported in this browser');
    }

    function sendSubscriptionToServer(subscription) {
        fetch('/subscription', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken // Защита от CSRF
            },
            body: JSON.stringify(subscription)
        });
    }
})();
