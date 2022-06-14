class Requests {
  static baseUrl = 'https://private-db987b-visolopenapi.apiary-mock.com'
  static portId = 1

  static async postData(path, data = {}) {
    const res = await fetch(this.baseUrl + path, {
      method: 'POST',
      body: JSON.stringify(data)
    });
    return res.json();
  }

  static async getData(path) {
    const res = await fetch(this.baseUrl + path);
    return res.json();
  }
}

export default Requests;
