class Requests {
  static baseUrl = 'http://localhost:8080/visol/rest'
  static portId = 1

  static async postData(path, data = {}) {
    const res = await fetch(this.baseUrl + path, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify(data)
    }).catch(e => console.log(e));
    return res.json();
  }

  static async getData(path) {
    const res = await fetch(this.baseUrl + path, {
      headers: {
        'Accept': 'application/json'
      },
    });
    return res.json();
  }
}

export default Requests;
