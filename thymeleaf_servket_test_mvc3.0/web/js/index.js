function delFruit(id) {
    if (confirm('是否确认删除？')){
        window.location.href="fruit.do?oper=delete&id="+id;
    }
}

function page(pageNo) {
    if(pageNo<1){
        pageNo = 1;
    }

    window.location.href="fruit.do?pageNo="+pageNo;

}