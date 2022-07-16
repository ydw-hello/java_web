function delFruit(id) {
    if (confirm('是否确认删除？')){
        window.location.href="del?id="+id;
    }
}

function page(pageNo) {
    if(pageNo<1){
        pageNo = 1;
    }

    window.location.href="index?pageNo="+pageNo;

}