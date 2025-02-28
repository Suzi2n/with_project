const token = searchParam('token')

if (token) {
    console.log("✅ 토큰 발견! 저장합니다:", token);
    localStorage.setItem("access_token", token)
    console.log("📌 저장된 Access Token:", localStorage.getItem('access_token'));
} else {
    console.warn("⚠️ URL에서 토큰을 찾을 수 없습니다.");
}

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}
