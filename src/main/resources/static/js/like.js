let stompClient = null;

// WebSocket接続
function connectWs() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => {
        console.log("WebSocket接続成功:", frame);

        // 開始
        stompClient.subscribe('/topic/likes', (message) => {
            const data = JSON.parse(message.body);
            const targetId = `like-count-${data.targetType}-${data.targetId}`;
            console.log("通知受信:", data, "探すID:", targetId);

            const span = document.getElementById(targetId);
            if (span) {
                console.log("更新前:", span.innerText);
                span.innerText = data.likeCount;
                console.log("更新後:", span.innerText);
            } else {
                console.warn("要素が見つかりません:", targetId);
            }
        });
    }, (error) => {
        console.error("WebSocket接続エラー:", error);
    });
}

// いいね登録
function likeItem(type, id) {
    fetch(`/api/likes?targetType=${type}&targetId=${id}`, { method: "POST" })
        .then(res =>
            res.json().then(data => {
                if (!res.ok) {
                    // 失敗時
                    alert(data.message || "エラーが発生しました");
                    throw new Error(data.message);
                }
                return data;
            })
        )
        .then(data => {
            // カウント更新
            const span = document.getElementById(`like-count-${data.targetType}-${data.targetId}`);
            if (span) span.innerText = data.likeCount;

            // ボタンに色を付ける（押した瞬間だけ見た目変更）
            const btn = document.getElementById(`like-btn-${data.targetType}-${data.targetId}`);
            if (btn) {
                btn.classList.add("liked");
                localStorage.setItem(`liked-${data.targetType}-${data.targetId}`, "true");
            }
        })
        .catch(err => {
            console.error("Like API error:", err);
        });
}

// ページ読み込み時に復元 + WebSocket接続
window.addEventListener("load", () => {
    connectWs();

    // 👇 localStorage を確認して、青くする
    for (let i = 0; i < localStorage.length; i++) {
        const key = localStorage.key(i);
        if (key.startsWith("liked-")) {
            const btnId = key.replace("liked-", "like-btn-");
            const btn = document.getElementById(btnId);
            if (btn) {
                btn.classList.add("liked");
            }
        }
    }
});

// ページ読み込み時にWebSocket接続
window.addEventListener("load", () => {
    connectWs();
});