// 当加载窗口时，调用匿名方法,绑定各种事件
window.onload=function () {
    // 根据id获取元素table
    var table = document.getElementById("tbl_fruit");

    // 获取表中行
    var rows = table.rows;
    for(var i=0;i<rows.length;i++){
        if(i>0&&i<rows.length-1){
            var row = rows[i];
            row.onmouseover=changeBGColor;
            row.onmouseout=clearBGColor;
            var cells = row.cells;
            var priceTD = cells[1];
            priceTD.onmouseover=showHand;
            priceTD.onclick=showInputText;
            subTotalPrice(row);

            // 获取图片
            var imgTd = cells[cells.length-1];
            var img = imgTd.firstChild;
            if(img&&img.tagName=='IMG'){
                img.onclick=delRow;
            }
        }
    }
    updatePrice();

    //--------------绑定添加表的相应事件
    var addTable = document.getElementById("add_table");
    var rows = addTable.rows;
    var row = rows[rows.length-1];
    var cells = row.cells;
    cells[0].onclick=addData;
    cells[1].onclick=resetData;
}
function resetData() {
    document.getElementById("fname").value='';
    document.getElementById("fprice").value='';
    document.getElementById("fcount").value='';
}
function addRow(name,price,num) {
    var table = document.getElementById("tbl_fruit");
    var rows = table.rows;
    var tr = table.insertRow(rows.length-1);
    var cell1 = tr.insertCell(0);
    cell1.innerText = name;
    var cell2 = tr.insertCell(1);
    cell2.innerText = price;
    var cell3 = tr.insertCell(2);
    cell3.innerText = num;
    var cell4 = tr.insertCell(3);
    var cell5 = tr.insertCell(4);
    cell5.innerHTML='<img src="img/del.jpeg" alt="" title="删除" class="delImg">'
    subTotalPrice(tr);

    // 绑定事件
    tr.onmouseover=changeBGColor;
    tr.onmouseout=clearBGColor;
    var cells = tr.cells;
    var priceTD = cells[1];
    priceTD.onmouseover=showHand;
    priceTD.onclick=showInputText;


    // 获取图片
    var imgTd = cells[cells.length-1];
    var img = imgTd.firstChild;
    if(img&&img.tagName=='IMG'){
        img.onclick=delRow;
    }
}
function addData() {
    // alert('add')
    var name = document.getElementById("fname").value;
    var price = document.getElementById("fprice").value;
    var num = document.getElementById("fcount").value;
    // 1.校验表单数据
    var flag = true;
    if(name==''){
        flag = false;
    }
    if (price==''){
        flag=false;
    }
    if (num==''){
        flag=false;
    }

    if (flag){
        // 向表中添加行
        addRow(name,price,num);
    }else{
        alert('有字段为空，请检查并输入')
    }
}

function delRow() {
    if (event&&event.srcElement&&event.srcElement.tagName=='IMG'){
        if(confirm('是否确认删除当前记录？')){
            var img = event.srcElement;
            var tr = img.parentElement.parentElement;
            var table = document.getElementById('tbl_fruit');
            table.deleteRow(tr.rowIndex)
            totalPrice();
        }

    }

}
// 点击td后，显示文本框
function showInputText() {
    if(event&&event.srcElement&&event.srcElement.tagName=='TD'){
        var td = event.srcElement;
        // 如果td里面有节点，而且是文本节点，则进行以下操作：
        if(td.firstChild&&td.firstChild.nodeType=='3'){
            var oldPrice = td.innerText;
            td.innerHTML='<input type="text" size="4" />';

            var input = td.firstChild;
            if(input&&input.tagName=='INPUT'){
                input.value = oldPrice;
                // 选中内部的文本
                input.select();
                // input失去焦点，重新计算小计
                input.onblur=updatePrice;
                // 在输入框上绑定键盘按下事件，以避免用户输入非法数据
                input.onkeydown=checkInput;
            }
        }
        // 否则，不操作

    }
}
// 检验键盘按下的值的方法(简易实现)
function checkInput() {
    // 0-9对应的码值为48-57 删除键 8 enter 13
    var keyCode = event.keyCode;

    if(!(keyCode>=48&&keyCode<=57||keyCode==8||keyCode==13)){
        event.returnValue=false;
    }

    if(keyCode==13){
        event.srcElement.blur();
    }


}
// 重新计算该行的小计
function updatePrice() {
    if(event&&event.srcElement&&event.srcElement.tagName=='INPUT'){
        // 将输入框重新变成文本
        var input = event.srcElement;
        var newPrice = input.value;
        if(newPrice==''){
            newPrice = 0;
        }
        var priceTd = input.parentElement;
        priceTd.innerText = newPrice;
        // 计算小计
        subTotalPrice(priceTd.parentElement);

    }
}
// 计算小计
function subTotalPrice(tr) {
    if(tr&&tr.tagName=='TR'){
        var cells = tr.cells;
        var subTotal = cells[cells.length-1-1];
        var num = cells[cells.length-3].innerText;
        var newPrice = cells[1].innerText;
        subTotal.innerText = parseInt(newPrice)*parseInt(num);
        // 重新计算小计后，重新计算总价
        totalPrice();
    }
}
// 计算总价
function totalPrice() {
    var table = document.getElementById('tbl_fruit');
    var rows = table.rows;
    var sum = 0;
    for(var i=1;i<rows.length-1 ;i++){
        var tr = rows[i];
        var cells = tr.cells;
        // console.log(cells[cells.length-2].innerText+'====')
        sum = sum+parseInt(cells[cells.length-2].innerText);
    }

    var tr = rows[rows.length-1];
    var totalPriceTd = tr.cells[1];

    totalPriceTd.innerText = sum;
}
// 当鼠标悬浮的时候，显示背景色，并将字体改成白色
function changeBGColor() {
    // alert('调用该方法')

    // 当前发生的事件：event
    // alert(event.srcElement); 事件源
    // alert(event.srcElement.tagName); TD
    if (event && event.srcElement && event.srcElement.tagName=='TD'){
        var td = event.srcElement;
        //td.parentElement: 获取td的父元素TR
        var tr = td.parentElement;
        // 如果想要通过js给某节点设置样式，需要加.style
        tr.style.backgroundColor='skyblue';

        // 设置行内单元格中的字体颜色
        var cells = tr.cells;
        for(var i=0;i<cells.length;i++){
            // 修改单元格中的字体颜色为白色
            cells[i].style.color = 'white';
        }

    }
}

// 当鼠标离开时，恢复原始样式
function clearBGColor() {
    if(event&&event.srcElement&&event.srcElement.tagName=='TD'){
        var td = event.srcElement;
        var tr = td.parentElement;
        // 将tr的背景色改为透明色
        tr.style.backgroundColor='transparent';
        // 改回字体颜色
        var cells = tr.cells;
        for(var i=0;i<cells.length;i++){
            cells[i].style.color = 'black'
        }
    }
}

// 当鼠标悬浮的时候，鼠标改为手的形状
function showHand() {
    if (event&&event.srcElement&&event.srcElement.tagName=='TD'){
        var td = event.srcElement;

        // cursor：光标
        td.style.cursor = 'hand';
    }

}

