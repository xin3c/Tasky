let swRegistration = null;

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

if ('serviceWorker' in navigator && 'PushManager' in window) {
    navigator.serviceWorker.register('/sw.js')
        .then(function(swReg) {
            console.log('Service Worker зарегистрирован', swReg);
            swRegistration = swReg;
            askPermission();
        })
        .catch(function(error) {
            console.error('Ошибка регистрации Service Worker', error);
        });
} else {
    console.warn('Пуш-уведомления не поддерживаются в этом браузере');
}

function askPermission() {
    Notification.requestPermission(function(status) {
        console.log('Статус разрешения уведомлений:', status);
        if (status === 'granted') {
            subscribeUser();
        }
    });
}

const applicationServerPublicKey = 'ВАШ_ПУБЛИЧНЫЙ_КЛЮЧ';

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
            console.log('Пользователь подписан:', subscription);
            sendSubscriptionToServer(subscription);
        })
        .catch(function(err) {
            console.log('Не удалось подписать пользователя:', err);
        });
}

function sendSubscriptionToServer(subscription) {
    fetch('/subscription', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(subscription)
    });
}