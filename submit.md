3 Number of available books
4 Any of the itemIDs from books in the catalog/all is valid
## Thoughts
It is incredible how much time I spent on this. I do not have much experience with javascript.
At least now I finally know what people without programming experience feel like doing this degree.

I struggled with the vagueness of js. For example, I accidentally used the taken field `this.response`, which caused an annoying bug.

At the end, I got it to work. :)

## index.js, loaded in index.html
```js
let jsonResponses;

function loadBooks(event) {
   if (event.code === "Enter") {
      let category = document.getElementById("search").value;
      if(!(category === "kids" || category === "tech")){
         console.log("Invalid category");
         document.getElementById("items").textContent = "Invalid category";
      }
      else {
         let xhr = new XMLHttpRequest();
         xhr.onload = function() {
            if (this.status == 200) {
               renderBooks(this.responseText);
            }
         };

         xhr.open("GET", "http://localhost:8080/visol/rest/" + category, true);
         xhr.send();
      }
   }
}

function addToCart(book) {
   let responseItem = {
      "item": book,
      "numItems": 1
   };
   let json = JSON.stringify([responseItem]);
   let xhr = new XMLHttpRequest();
   xhr.onreadystatechange = function() {
      if(this.readyState === 4) {
         refreshCosts();
      }
   };
   xhr.open("POST", "http://localhost:8080/visol/rest/cart", true);
   xhr.setRequestHeader("Content-Type", "application/json");
   xhr.send(json);
}

function refreshCosts() {
   let xhr = new XMLHttpRequest();
   xhr.onreadystatechange = function() {
      if (this.readyState === 4 && this.status === 200) {
         document.getElementById("costs").textContent = this.responseText;
      }
   };
   xhr.open("GET", "http://localhost:8080/visol/rest/cart/costs", true);
   xhr.send();
}

function renderBooks(responseText) {
    jsonResponses = JSON.parse(responseText);
    let table = document.createElement("table");
    let itemsElement = document.getElementById("items");
    itemsElement.replaceChildren(table);

    for (i in jsonResponses) {
        let row = document.createElement("tr");
        table.appendChild(row);

        let column1 = document.createElement("td");
        row.appendChild(column1);
        column1.innerHTML = jsonResponses[i].shortDescription;

        let column2 = document.createElement("td");
        row.appendChild(column2);
        let button = document.createElement("button");
        button.setAttribute("onclick", "addToCart(" + JSON.stringify(jsonResponses[i]) + ")");
        button.innerText = "Order!";
        column2.appendChild(button);
    }
}
```
## checkout.js, loaded in checkout.html
```js
let xhr = new XMLHttpRequest();
xhr.open('GET', 'http://localhost:8080/visol/rest/cart', true);
xhr.onload=function(){
    if (this.status === 200) {
        renderBooks(this.responseText);
    } else {
        console.log('state ' + this.readyState + '\tstatus: ' + this.status);
    }
};
xhr.send()

function renderBooks(responseText) {
    jsonResponses = JSON.parse(responseText);
    let itemsElement = document.getElementById("items");
    let table = document.createElement("table");
    itemsElement.replaceChildren(table);

    for (i in jsonResponses) {
        let book = jsonResponses[i].item;
        let row = document.createElement("tr");
        table.appendChild(row);

        let column1 = document.createElement("td");
        row.appendChild(column1);
        let parsedHtml = new DOMParser().parseFromString(book.shortDescription, "text/html").body;
        column1.appendChild(parsedHtml);

        let column2 = document.createElement("td");
        row.appendChild(column2);
        column2.textContent = jsonResponses[i].numItems + " x " + book.cost;

        let column3 = document.createElement("td");
        row.appendChild(column3);
        column3.textContent = jsonResponses[i].numItems * book.cost;
    }
}

```