function fetchUserInfo() {
    fetch("/runner/user_info")
        .then(response => response.json())
        .then(response => {
            if (response.message == "success") {
                const name = response.data.first_name + " " + response.data.last_name;
                // Add user info to the page
                const sidebar = document.getElementById('sidebar');
                if (sidebar) {
                    const userInfo = document.createElement('div');
                    userInfo.href = "#";
                    userInfo.innerText = `Welcome, ${name}!`;
                    userInfo.classList.add('user-info');
                    sidebar.insertBefore(userInfo, sidebar.firstChild);
                }
            }
        })
        .catch(error => console.error("Error fetching user info:", error));
}

window.addEventListener("load", function() {
    fetchUserInfo();
})