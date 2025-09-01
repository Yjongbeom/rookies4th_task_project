// UI 관리 모듈 - 구조분해할당과 화살표 함수 사용

// 메시지 스타일 설정
const messageStyles = {
    success: {
        color: '#28a745',
        backgroundColor: '#d4edda',
        borderColor: '#c3e6cb'
    },
    error: {
        color: '#dc3545',
        backgroundColor: '#f8d7da',
        borderColor: '#f5c6cb'
    }
}

// UI 상태 관리
let messageTimer = null

// UI 서비스 객체
export const uiService = {
    // 성공 메시지 표시 (화살표 함수)
    showSuccess: (message) => {
        uiService.showMessage(message, 'success')
    },

    // 에러 메시지 표시 (화살표 함수)
    showError: (message) => {
        uiService.showMessage(message, 'error')
    },

    // 메시지 표시 함수 (구조분해할당 활용)
    showMessage: (message, type = 'error') => {
        const errorSpan = document.getElementById('formError')
        if (!errorSpan) return

        uiService.clearMessageTimer()

        const { color, backgroundColor, borderColor } = messageStyles[type] || messageStyles.error

        errorSpan.textContent = message
        errorSpan.style.display = 'block'
        errorSpan.style.color = color
        errorSpan.style.backgroundColor = backgroundColor
        errorSpan.style.borderColor = borderColor

        const duration = type === 'success' ? 3000 : 5000

        messageTimer = setTimeout(() => {
            uiService.hideMessage()
        }, duration)
    },

    // 메시지를 숨기고 스타일을 초기화하는 함수 (화살표 함수)
    hideMessage: () => {
        const errorSpan = document.getElementById('formError')
        if (!errorSpan) return

        errorSpan.style.display = 'none'
        errorSpan.style.backgroundColor = ''
        errorSpan.style.borderColor = ''

        uiService.clearMessageTimer()
    },

    // 메시지 자동 숨김 타이머를 해제하는 함수 (화살표 함수)
    clearMessageTimer: () => {
        if (messageTimer) {
            clearTimeout(messageTimer)
            messageTimer = null
        }
    },

    // 버튼의 로딩 상태를 관리하는 함수 (구조분해할당)
    setButtonLoading: (button, isLoading = false, text = '') => {
        if (!button) return

        button.disabled = isLoading

        if (text) {
            button.textContent = text
        }

        if (isLoading) {
            button.style.opacity = '0.7'
            button.style.cursor = 'not-allowed'
        } else {
            button.style.opacity = '1'
            button.style.cursor = 'pointer'
        }
    }
}