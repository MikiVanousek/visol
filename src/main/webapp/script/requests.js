class Requests {
  static baseUrl = 'http://localhost:8080/visol/rest'
  static headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }

  static async postData(path, data = {}) {
    let res = await fetch(this.baseUrl + path, {
      method: 'POST',
      headers: Requests.headers,
      body: JSON.stringify(data)
    });
    if (res.status !== 200) {
      throw new Error('GET request failed: ' + res.status)
    }
    return res.json()
  }

  static async getData(path) {
    let res = await fetch(this.baseUrl + path, {
      headers: Requests.headers,
    });
    if (res.status !== 200) {
      throw new Error('GET request failed: ' + res.status)
    }
    return res.json()
  }
}

export default Requests;
