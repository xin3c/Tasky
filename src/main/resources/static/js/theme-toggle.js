document.addEventListener('DOMContentLoaded', () => {
    const themeToggleBtn = document.getElementById('theme-toggle');
    const lightIcons = document.querySelector('.light-icons');
    const darkIcons = document.querySelector('.dark-icons');

    const userPrefersDark = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
    const currentTheme = localStorage.getItem('theme') || (userPrefersDark ? 'dark' : 'light');
    setTheme(currentTheme);

    themeToggleBtn.addEventListener('click', () => {
        const theme = document.documentElement.getAttribute('data-theme') === 'dark' ? 'light' : 'dark';
        setTheme(theme);
        try {
            setIconsTheme(theme)
            updateIcons();
        }   catch (error) {
            console.error(error);

         }
    });

    function setTheme(theme) {
        document.documentElement.setAttribute('data-theme', theme);
        localStorage.setItem('theme', theme);
        themeToggleBtn.textContent = theme === 'dark' ? '☀️ Light Mode' : '🌙 Dark Mode';
        }

    function setIconsTheme(theme) {
        const iconPath = theme === 'dark' ? '/icons/dark/' : '/icons/light/';
        document.documentElement.style.setProperty('--icon-path', iconPath);
        const selectedIcon = document.getElementById('selected-icon');
        const iconIdField = document.getElementById('icon_id');
        selectedIcon.src = iconPath + iconIdField.value;
    }
    function updateIcons() {
        const theme = document.documentElement.getAttribute('data-theme');
        const iconPath = theme === 'dark' ? '/icons/dark/' : '/icons/light/';
        document.querySelectorAll('.icon-option').forEach(icon => {
            const iconId = icon.getAttribute('data-icon-id');
            icon.src = iconPath + iconId;
        });
    }
});
