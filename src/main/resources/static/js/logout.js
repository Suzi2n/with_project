$(function () {
    $(document).on('click', '#logout-btn', function () {
        console.log("✅ 로그아웃 버튼이 클릭됨");

        const loginTypeInput = document.getElementById('login-type');

        if (!loginTypeInput) {
            console.error("❌ login-type 요소를 찾을 수 없습니다.");
            return;
        }

        const loginType = loginTypeInput.value;
        console.log("🔍 로그인 타입:", loginType);

        if (loginType === 'oauth') {
            console.log("🔄 OAuth 로그아웃 진행");
            oauthLogout();
        } else if (loginType === 'regular') {
            console.log("🔄 일반 로그아웃 진행");
            regularLogout();
        } else {
            console.error("❌ 알 수 없는 로그인 타입:", loginType);
        }
    });

    // ✅ `access_token`이 없거나 null이면 로그아웃 진행
    function validateAccessTokenBeforeLogout() {
        const accessToken = localStorage.getItem('access_token');
        if (!accessToken || accessToken.trim() === '') {
            console.error("❌ 유효하지 않은 Access Token!");
            alert('세션이 만료되었습니다. 다시 로그인해 주세요.');
            location.replace('/login');
            return false;
        }
        return true;
    }

    // ✅ OAuth 로그아웃
    function oauthLogout() {
        console.log("🔄 OAuth 로그아웃 요청 중...");

        console.log("🔍 저장된 Refresh Token:", getCookie('refresh_token'));

        const accessToken = localStorage.getItem('access_token');

        console.log("🔍 현재 Access Token:", accessToken);   ///// httpRequest()에서 accessToken이 null 또는 undefined인지 확인

        if (!validateAccessTokenBeforeLogout()) return;

        httpRequest('DELETE', '/api/refresh-token', null,
            function success() {
                alert('OAuth 로그아웃이 완료되었습니다.');
                localStorage.removeItem('access_token');
                deleteCookie('refresh_token');
                location.replace('/login');
            },
            function fail() {
                alert('OAuth 로그아웃에 실패했습니다.');
            }
        );
    }

    // ✅ 일반 로그아웃
    function regularLogout() {
        console.log("🔄 일반 로그아웃 요청 중...");

        fetch('/logout', {
            method: 'POST',
            credentials: 'include'
        }).then(response => {
            if (response.ok || response.status === 302) {
                alert('일반 로그아웃이 완료되었습니다.');
                location.replace('/login');
            } else {
                alert('❌ 일반 로그아웃에 실패했습니다.');
            }
        }).catch(error => {
            console.error("🚨 로그아웃 요청 중 오류 발생:", error);
            alert('❌ 로그아웃 요청 중 오류가 발생했습니다.');
        });
    }

    // ✅ HTTP 요청 함수 (jQuery 버전)
    function httpRequest(method, url, body, success, fail) {


        const accessToken = localStorage.getItem('access_token');


        if (!validateAccessTokenBeforeLogout()) return;

        $.ajax({
            url: url,
            type: method,
            contentType: 'application/json',
            headers: {
                Authorization: 'Bearer ' + accessToken
            },
            data: body ? JSON.stringify(body) : null
        }).done(function () {
            success();
        }).fail(function (jqXHR) {
            if (jqXHR.status === 401 && getCookie('refresh_token')) {
                console.log("🔄 Access Token 만료됨, 새 토큰 요청 중...");

                $.ajax({
                    url: '/api/token',
                    type: 'POST',
                    contentType: 'application/json',
                    headers: {
                        Authorization: 'Bearer ' + accessToken
                    },
                    data: JSON.stringify({ refreshToken: getCookie('refresh_token') })
                }).done(function (result) {
                    console.log("✅ 새 Access Token 발급 완료");
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                }).fail(function () {
                    console.error("❌ 토큰 갱신 실패, 로그아웃 진행");
                    fail();
                });
            } else {
                fail();
            }
        });
    }

    // ✅ 쿠키 가져오는 함수
    function getCookie(key) {
        const cookies = document.cookie.split('; ');
        for (const item of cookies) {
            const [cookieKey, value] = item.split('=');
            if (cookieKey === key) {
                return value;
            }
        }
        return null;
    }

    // ✅ 쿠키 삭제 함수
    function deleteCookie(name) {
        document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/';
    }
});
