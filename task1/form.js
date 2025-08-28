const API_BASE_URL = "http://localhost:8080";
const form = document.getElementById("bookForm");
const tableBody = document.getElementById("bookTableBody");

let editingBookId = null; 

const submitButton = form.querySelector("button[type='submit']");
const cancelButton = form.querySelector("button[type='reset']");

function showError(message) {
  document.getElementById("errorMessage").textContent = message;
}
function clearError() {
  document.getElementById("errorMessage").textContent = "";
}

function validateBook(book) {
  if (!book.title || book.title.trim() === "") {
    showError("제목을 입력하세요.");
    return false;
  }
  if (!book.author || book.author.trim() === "") {
    showError("저자를 입력하세요.");
    return false;
  }
  if (!/^[0-9Xx-]+$/.test(book.isbn)) {
    showError("ISBN은 숫자, 하이픈(-), X만 가능합니다.");
    return false;
  }
  if (isNaN(book.price) || Number(book.price) <= 0) {
    showError("가격은 양수 숫자로 입력하세요.");
    return false;
  }
  if (!book.publishDate) {
    showError("출판일을 입력하세요.");
    return false;
  }
  clearError();
  return true;
}

function createBook(bookData) {
  return fetch(`${API_BASE_URL}/api/books`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(bookData),
  }).then((res) => {
    if (!res.ok) throw new Error("책 등록 실패");
    return res.json();
  });
}

function deleteBook(bookId) {
  return fetch(`${API_BASE_URL}/api/books/${bookId}`, {
    method: "DELETE",
  }).then((res) => {
    if (!res.ok) throw new Error("책 삭제 실패");
  });
}

function editBook(bookId) {
  fetch(`${API_BASE_URL}/api/books/${bookId}`)
    .then((res) => {
      if (!res.ok) throw new Error("책 불러오기 실패");
      return res.json();
    })
    .then((book) => {
      document.getElementById("title").value = book.title;
      document.getElementById("author").value = book.author;
      document.getElementById("isbn").value = book.isbn;
      document.getElementById("price").value = book.price;
      document.getElementById("publishDate").value = book.publishDate;

      editingBookId = bookId;
      submitButton.textContent = "수정 저장";
      cancelButton.style.display = "inline-block";
    })
    .catch((err) => showError(err.message));
}

function updateBook(bookId, bookData) {
  return fetch(`${API_BASE_URL}/api/books/${bookId}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(bookData),
  }).then((res) => {
    if (!res.ok) throw new Error("책 수정 실패");
    return res.json();
  });
}

function addRow(book) {
  const row = document.createElement("tr");
  row.innerHTML = `
    <td>${book.title}</td>
    <td>${book.author}</td>
    <td>${book.isbn}</td>
    <td>${book.price}</td>
    <td>${book.publishDate}</td>
    <td>
      <button onclick="editBook(${book.id})">수정</button>
      <button onclick="handleDelete(${book.id}, this)">삭제</button>
    </td>
  `;
  tableBody.appendChild(row);
}

form.addEventListener("submit", function (event) {
  event.preventDefault();

  const formData = new FormData(form);
  const bookData = {};
  formData.forEach((value, key) => {
    bookData[key] = value;
  });

  if (!validateBook(bookData)) return;

  if (editingBookId) {
    updateBook(editingBookId, bookData)
      .then((updated) => {
        alert("수정 완료!");
        location.reload();
      })
      .catch((err) => showError(err.message));
  } else {
    createBook(bookData)
      .then((newBook) => {
        alert("등록 완료!");
        addRow(newBook);
        form.reset();
      })
      .catch((err) => showError(err.message));
  }
});

cancelButton.addEventListener("click", function () {
  editingBookId = null;
  submitButton.textContent = "등록";
  cancelButton.style.display = "none";
  form.reset();
  clearError();
});

function handleDelete(bookId, btn) {
  if (!confirm("정말 삭제하시겠습니까?")) return;
  deleteBook(bookId)
    .then(() => {
      alert("삭제 완료!");
      btn.closest("tr").remove();
    })
    .catch((err) => showError(err.message));
}