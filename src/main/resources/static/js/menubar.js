/*
document.addEventListener("DOMContentLoaded", function () {
    const menuBtn = document.getElementById("menu-btn");
    const closeMenuBtn = document.getElementById("close-menu");
    const sidebarMenu = document.getElementById("sidebar-menu");

    // 메뉴 열기
    menuBtn.addEventListener("click", function () {
        sidebarMenu.style.right = "0";  // 화면에 보이게 설정
    });

    // 메뉴 닫기
    closeMenuBtn.addEventListener("click", function () {
        sidebarMenu.style.right = "-250px";  // 다시 숨김
    });

    // 화면 바깥 클릭 시 메뉴 닫기
    document.addEventListener("click", function (event) {
        if (!sidebarMenu.contains(event.target) && !menuBtn.contains(event.target)) {
            sidebarMenu.style.right = "-250px";
        }
    });
});

 */


document.addEventListener("DOMContentLoaded", function () {
    const menuBtn = document.getElementById("menu-btn");
    const menuPopup = document.getElementById("menu-popup");

    // 메뉴 열기/닫기 토글
    menuBtn.addEventListener("click", function (event) {
        event.stopPropagation(); // 클릭 이벤트 버블링 방지

        // 햄버거 버튼 위치 계산
        const rect = menuBtn.getBoundingClientRect();
        menuPopup.style.top = `${rect.bottom + window.scrollY}px`; // 버튼 아래로 위치
        menuPopup.style.right = `${window.innerWidth - rect.right - 10}px`; // 버튼 오른쪽 정렬

        menuPopup.classList.toggle("show");
    });

    // 바깥 클릭하면 닫힘
    document.addEventListener("click", function (event) {
        if (!menuPopup.contains(event.target) && event.target !== menuBtn) {
            menuPopup.classList.remove("show");
        }
    });
});
