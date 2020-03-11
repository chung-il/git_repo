function checkBoardWrite() {
	//alert("test")
	var frm = document.boardWriteForm;
	if(!frm.subject.value){
		alert("제목을입력하세요")
		frm.subject.focus();
	}else if(!frm.content.value){
		alert("내용을입력하세요")
		frm.content.focus();
	}else{
		frm.submit();
	}
}