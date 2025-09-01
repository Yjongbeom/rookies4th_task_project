// API 서비스 모듈 - async/await와 구조분해할당 사용

// 서버 API의 기본 URL 주소
const API_BASE_URL = 'http://localhost:8080'

// HTTP 요청을 위한 공통 함수 (async/await)
const request = async (endpoint, options = {}) => {
    const url = `${API_BASE_URL}${endpoint}`
    const { method = 'GET', body, headers = {} } = options
    
    const requestOptions = {
        method,
        headers: {
            'Content-Type': 'application/json',
            ...headers
        }
    }
    
    if (body) {
        requestOptions.body = JSON.stringify(body)
    }
    
    try {
        console.log(`API 요청: ${method} ${url}`)
        const response = await fetch(url, requestOptions)
        
        if (!response.ok) {
            const errorData = await response.json()
            const errorMessage = getErrorMessage(response.status, errorData)
            throw new Error(errorMessage)
        }
        
        if (method === 'DELETE') {
            return null
        }
        
        return await response.json()
        
    } catch (error) {
        console.error('API 요청 오류:', error)
        
        if (error.name === 'TypeError') {
            throw new Error('네트워크 연결을 확인해주세요.')
        }
        
        throw error
    }
}

// 에러 메시지 생성 함수 (화살표 함수)
const getErrorMessage = (status, errorData) => {
    const serverMessage = errorData.message || '알 수 없는 오류가 발생했습니다.'
    
    if (status === 400) {
        return `입력데이터 오류 : ${serverMessage}`
    }
    if (status === 404) {
        return `데이터가 존재하지 않음 : ${serverMessage}`
    }
    if (status === 409) {
        return `중복 오류: ${serverMessage}`
    }
    if (status === 500) {
        return `서버 오류: ${serverMessage}`
    }
    
    return `오류 (${status}): ${serverMessage}`
}

// API 서비스 객체 - 모든 도서 관련 API 호출을 담당
export const apiService = {
    // 도서 목록 조회
    getBooks: async () => {
        return await request('/api/books')
    },
    
    // 특정 도서 조회  
    getBook: async (bookId) => {
        if (!bookId) {
            throw new Error('도서 ID가 필요합니다.')
        }
        
        return await request(`/api/books/${bookId}`)
    },
    
    // 도서 생성
    createBook: async (bookData) => {
        if (!bookData) {
            throw new Error('도서 데이터가 필요합니다.')
        }
        
        return await request('/api/books', {
            method: 'POST',
            body: bookData
        })
    },
    
    // 도서 수정
    updateBook: async (bookId, bookData) => {
        if (!bookId) {
            throw new Error('도서 ID가 필요합니다.')
        }
        if (!bookData) {
            throw new Error('도서 데이터가 필요합니다.')
        }
        
        return await request(`/api/books/${bookId}`, {
            method: 'PUT',
            body: bookData
        })
    },
    
    // 도서 삭제
    deleteBook: async (bookId) => {
        if (!bookId) {
            throw new Error('도서 ID가 필요합니다.')
        }
        
        return await request(`/api/books/${bookId}`, {
            method: 'DELETE'
        })
    }
}