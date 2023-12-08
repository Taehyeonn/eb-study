function validateForm() {
    let category = document.getElementById("category").value.trim();
    let writer = document.getElementById("writer").value.trim();
    let password = document.getElementById("password").value.trim();
    let confirmPassword = document.getElementById("confirmPassword").value.trim();
    let title = document.getElementById("title").value;
    let content = document.getElementById("content").value;
    // let password = document.getElementById("password").value; 파일


    /**
     * 파라미터의 유효성 검증 후 통과하지 못할 경우 false 반환하여 form 데이터들을 서버로 보내지 않음
     */
    if (isNotValidCategory(category)){
        alert("카테고리를 선택해주세요.");
        return false;
    }

    if (isNotValidWriter(writer)){
        alert("작성자는 3글자 이상 5글자 미만이어야 합니다.");
        return false;
    }

    if (isNotPasswordMatching(password, confirmPassword)){
        alert("비밀번호가 일치하지 않습니다. ");
        return false;
    }

    if (isNotValidPassword(password)){
        alert("비밀번호는 4글자 이상 16글자 미만, 영문/숫자/특수문자(@#$%^&+=) 포함되어야 합니다. ");
        return false;
    }

    if (isNotValidTitle(title)){
        alert("제목은 4글자 이상 100글자 미만이어야 합니다.");
        return false;
    }

    if (isNotValidContent(content)){
        alert("내용은 4글자 이상 2000글자 미만이어야 합니다.");
        return false;
    }

    return true;
}


// 카테고리를 고르지 않았으면 true 반환
function isNotValidCategory(category) {

    return category === "";
}

// 작성자가 공백이거나 3글자 이상 5글자 미만이면 true 반환
function isNotValidWriter(writer) {

    return writer === "" || writer.length < 3 || writer.length >= 5;
}

// 두 비밀번호가 일치하지 않으면 true 반환
function isNotPasswordMatching(password, confirmPassword) {

    return password !== confirmPassword;
}

// 비밀번호에 영문, 숫자, 특수문자가 포함되어 있지 않거나 길이가 4글자 이상, 16글자 미만이 아니면 true 반환
function isNotValidPassword(password) {
    if (password.length < 4 || password.length >= 16) {
        return true;
    }

    // 영문, 숫자, 특수문자를 각각 한 글자 이상 포함하는지 확인
    let letterPattern = /[a-zA-Z]/;
    let numberPattern = /[0-9]/;
    let specialCharPattern = /[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]/;

    // 하나라도 패턴과 맞지 않으면 true 반환
    return !(letterPattern.test(password) && numberPattern.test(password) && specialCharPattern.test(password));
}

// 제목이 공백이거나 4글자 이상, 100글자 미만이면 true 반환
function isNotValidTitle(title) {

    return title === "" || title.length() < 4 || title.length() >= 100;
}

// 내용이 공백이거나 4글자 이상, 2000글자 미만이면 true 반환
function isNotValidContent(content) {

    return content === "" || content.length() < 4 || content.length() >= 2000;
}