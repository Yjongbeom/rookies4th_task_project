// 유효성 검사 모듈 - 구조분해할당과 화살표 함수 사용

// 정규식 패턴들
export const patterns = {
    // ISBN 패턴: ISBN-10 또는 ISBN-13 형식
    isbn: /^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$/,
    
    // 출판년도 패턴: 1900-2030 사이의 숫자
    publicationYear: /^(19[0-9]{2}|20[0-2][0-9]|2030)$/
}

// 에러 메시지들
export const messages = {
    required: {
        title: '도서명을 입력해주세요.',
        isbn: 'ISBN을 입력해주세요.',
        author: '저자를 입력해주세요.'
    },
    
    format: {
        isbn: '올바른 ISBN 형식이 아닙니다. 예: 978-3-16-148410-0',
        publicationYear: '출판년도는 1900년부터 2030년 사이여야 합니다.'
    }
}

// 개별 필드별 검증 함수들
const validators = {
    // 도서명 필드 검증 함수
    title: (title) => {
        if (!title || title.trim().length === 0) {
            return { 
                isValid: false,
                message: messages.required.title,
                field: 'title'
            }
        }
        
        if (title.trim().length < 1) {
            return { 
                isValid: false, 
                message: '도서명은 최소 1글자 이상이어야 합니다.', 
                field: 'title' 
            }
        }
        
        return { isValid: true }
    },
    
    // ISBN 필드 검증 함수
    isbn: (isbn) => {
        if (!isbn || isbn.trim().length === 0) {
            return { 
                isValid: false, 
                message: messages.required.isbn, 
                field: 'isbn' 
            }
        }
        
        if (!patterns.isbn.test(isbn.trim())) {
            return { 
                isValid: false, 
                message: messages.format.isbn, 
                field: 'isbn' 
            }
        }
        
        return { isValid: true }
    },
    
    // 저자 필드 검증 함수
    author: (author) => {
        if (!author || author.trim().length === 0) {
            return { 
                isValid: false, 
                message: messages.required.author, 
                field: 'author' 
            }
        }
        
        if (author.trim().length < 2) {
            return { 
                isValid: false, 
                message: '저자명은 최소 2글자 이상이어야 합니다.', 
                field: 'author' 
            }
        }
        
        return { isValid: true }
    },
    
    // 출판사 필드 검증 함수
    publisher: (publisher) => {
        // 출판사는 선택사항이므로 빈 값이어도 통과
        if (!publisher || publisher.trim().length === 0) {
            return { isValid: true }
        }
        
        if (publisher.trim().length < 2) {
            return { 
                isValid: false, 
                message: '출판사명은 최소 2글자 이상이어야 합니다.', 
                field: 'publisher' 
            }
        }
        
        return { isValid: true }
    },
    
    // 출판년도 필드 검증 함수
    publicationYear: (publicationYear) => {
        // 출판년도는 선택사항이므로 빈 값이어도 통과
        if (!publicationYear || publicationYear.toString().trim().length === 0) {
            return { isValid: true }
        }
        
        const year = parseInt(publicationYear);
        if (isNaN(year) || year < 1900 || year > 2030) {
            return { 
                isValid: false, 
                message: messages.format.publicationYear, 
                field: 'publicationYear' 
            }
        }
        
        return { isValid: true }
    },
    
    // 장르 필드 검증 함수
    genre: (genre) => {
        // 장르는 선택사항이므로 빈 값이어도 통과
        return { isValid: true }
    }
}

// 메인 검증 함수 - 도서 객체 전체를 검증
export const validateBook = (book) => {
    if (!book) {
        return { isValid: false, message: '도서 데이터가 필요합니다.' }
    }
    
    const { title, isbn, author, publisher, publicationYear, genre } = book
    
    // 기본 필드들 순차적 검증
    const titleResult = validators.title(title)
    if (!titleResult.isValid) {
        return titleResult
    }
    
    const isbnResult = validators.isbn(isbn)
    if (!isbnResult.isValid) {
        return isbnResult
    }
    
    const authorResult = validators.author(author)
    if (!authorResult.isValid) {
        return authorResult
    }
    
    // 선택 필드들 검증
    const publisherResult = validators.publisher(publisher)
    if (!publisherResult.isValid) {
        return publisherResult
    }
    
    const yearResult = validators.publicationYear(publicationYear)
    if (!yearResult.isValid) {
        return yearResult
    }
    
    const genreResult = validators.genre(genre)
    if (!genreResult.isValid) {
        return genreResult
    }
    
    return { isValid: true }
}

// 실시간 검증 함수
export const validateField = (fieldName, value) => {
    const validator = validators[fieldName]
    
    if (!validator) {
        return { 
            isValid: true,
            message: '알 수 없는 필드입니다.' 
        }
    }
    
    return validator(value)
}