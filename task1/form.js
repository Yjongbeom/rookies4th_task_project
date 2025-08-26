const API_BASE_URL = "http://localhost:8080";
const form = document.getElementById("bookForm");
const tableBody = document.getElementById("bookTableBody");

function validateStudent(student) {
  if (!student.title || student.title.trim() === "") {
    alert("제목을 입력하세요.");
    return false;
  }
  if (!student.author || student.author.trim() === "") {
    alert("저자를 입력하세요.");
    return false;
  }
  if (!/^[0-9Xx-]+$/.test(student.isbn)) {
    alert("ISBN은 숫자, 하이픈(-), X만 가능합니다.");
    return false;
  }
  if (isNaN(student.price) || Number(student.price) <= 0) {
    alert("가격은 양수 숫자로 입력하세요.");
    return false;
  }
  if (!student.publishDate) {
    alert("출판일을 입력하세요.");
    return false;
  }
  return true;
}

form.addEventListener("submit", function (event) {
  event.preventDefault();

  const formData = new FormData(form);
  const bookData = {};
  formData.forEach((value, key) => {
    bookData[key] = value;
  });

  if (!validateStudent(bookData)) {
    return;
  }

  const row = document.createElement("tr");
  row.innerHTML = `
    <td>${bookData.title}</td>
    <td>${bookData.author}</td>
    <td>${bookData.isbn}</td>
    <td>${bookData.price}</td>
    <td>${bookData.publishDate}</td>
  `;
  tableBody.appendChild(row);

  fetch(`${API_BASE_URL}/api/books`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(bookData),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error("서버 오류 발생");
      }
      return res.json();
    })
    .then((data) => {
      console.log("서버 응답:", data);
      alert("서버에 저장되었습니다!");
    })
    .catch((err) => {
      console.error(err);
      alert("서버 통신에 실패했습니다.");
    });
});
