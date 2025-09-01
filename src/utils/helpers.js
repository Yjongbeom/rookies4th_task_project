// 유틸리티 헬퍼 함수들 - 구조분해할당과 화살표 함수 사용

// 날짜 포맷팅 함수 (화살표 함수와 기본값)
export const formatDate = (dateString, locale = 'ko-KR') => {
    if (!dateString) return '-'
    
    try {
        const date = new Date(dateString)
        
        // 유효한 날짜인지 확인
        if (isNaN(date.getTime())) {
            return dateString
        }
        
        return date.toLocaleDateString(locale)
    } catch (error) {
        console.error('날짜 포맷팅 오류:', error)
        return dateString
    }
}
// stringUtils.safeTrim() ,  const { safeTrim, isEmpty } = stringUtils;
// 문자열 유틸리티 (화살표 함수)
export const stringUtils = {
    // 안전한 trim
    safeTrim: (str) => {
        if (!str) return ''
        return str.toString().trim()
    },
    
    // 빈 문자열 체크
    isEmpty: (str) => {
        if (!str) return true
        return str.toString().trim().length === 0
    },
    
    // 문자열 자르기 (구조분해할당)
    truncate: (str, maxLength = 50) => {
        if (!str || str.length <= maxLength) return str || ''
        return str.substring(0, maxLength) + '...'
    },
    
    // 첫 글자 대문자
    capitalize: (str) => {
        if (!str) return ''
        return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase()
    },
    
    // 전화번호에서 숫자만 추출
    extractNumbers: (str) => {
        if (!str) return ''
        return str.replace(/[^0-9]/g, '')
    }
}

// 배열 유틸리티 (화살표 함수)
export const arrayUtils = {
    // 빈 배열 체크
    isEmpty: (arr) => {
        return !Array.isArray(arr) || arr.length === 0
    },
    
    // 중복 제거
    unique: (arr) => {
        if (!Array.isArray(arr)) return []
        return [...new Set(arr)]
    },
    
    // 배열 정렬 (구조분해할당)
    sortBy: (arr, key, ascending = true) => {
        if (!Array.isArray(arr)) return []
        
        return [...arr].sort((a, b) => {
            const aValue = a[key]
            const bValue = b[key]
            
            if (aValue === bValue) return 0
            
            const comparison = aValue > bValue ? 1 : -1
            return ascending ? comparison : -comparison
        })
    }
}

// DOM 유틸리티 (화살표 함수)
export const domUtils = {
    // 요소가 존재하는지 확인
    exists: (selector) => {
        return Boolean(document.querySelector(selector))
    },
    
    // 모든 요소 선택
    selectAll: (selector) => {
        return Array.from(document.querySelectorAll(selector))
    },
    
    // 클래스 토글
    toggleClass: (element, className) => {
        if (!element) return false
        return element.classList.toggle(className)
    },
    
    // 스크롤을 맨 위로 이동
    scrollToTop: (smooth = true) => {
        window.scrollTo({
            top: 0,
            behavior: smooth ? 'smooth' : 'auto'
        })
    }
}

// 로컬 스토리지 유틸리티 (에러 처리 포함)
export const storageUtils = {
    // 데이터 저장 (화살표 함수)
    set: (key, value) => {
        try {
            //object => json으로 변환
            const data = JSON.stringify(value)
            localStorage.setItem(key, data)
            return true
        } catch (error) {
            console.error('로컬 스토리지 저장 오류:', error)
            return false
        }
    },
    
    // 데이터 가져오기 (기본값 지원)
    get: (key, defaultValue = null) => {
        try {
            const item = localStorage.getItem(key)
            if (item === null) return defaultValue
            //json => object
            return JSON.parse(item)
        } catch (error) {
            console.error('로컬 스토리지 읽기 오류:', error)
            return defaultValue
        }
    },
    
    // 데이터 제거
    remove: (key) => {
        try {
            localStorage.removeItem(key)
            return true
        } catch (error) {
            console.error('로컬 스토리지 삭제 오류:', error)
            return false
        }
    },
    
    // 전체 초기화
    clear: () => {
        try {
            localStorage.clear()
            return true
        } catch (error) {
            console.error('로컬 스토리지 초기화 오료:', error)
            return false
        }
    }
}

// 랜덤 ID 생성 (화살표 함수)
export const generateId = (prefix = 'id') => {
    const timestamp = Date.now()
    const random = Math.random().toString(36).substring(2, 9)
    return `${prefix}_${timestamp}_${random}`
}

// 객체 깊은 복사 (간단한 버전)
export const deepClone = (obj) => {
    if (obj === null || typeof obj !== 'object') {
        return obj
    }
    
    if (obj instanceof Date) {
        return new Date(obj.getTime())
    }
    
    if (obj instanceof Array) {
        return obj.map(item => deepClone(item))
    }
    
    // 일반 객체인 경우
    const cloned = {}
    for (const key in obj) {
        if (obj.hasOwnProperty(key)) {
            cloned[key] = deepClone(obj[key])
        }
    }
    return cloned
}