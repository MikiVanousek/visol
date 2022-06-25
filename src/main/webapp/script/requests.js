import ErrorToast from './components/error-toast.js';

class Requests {
  static baseUrl = 'http://localhost:8080/visol/rest';
  static headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  };

  static async postData(path, data = {}, headers = {}) {
    const res = await fetch(this.baseUrl + path, {
      method: 'POST',
      headers: {...Requests.headers, ...headers},
      body: JSON.stringify(data),
    });
    if (!res.ok) {
      ErrorToast.show();
      throw new Error(res.statusText);
    }
    return res;
  }

  static async getData(path, headers = {}) {
    const res = await fetch(this.baseUrl + path, {
      headers: {...Requests.headers, ...headers},
    });
    if (!res.ok) {
      ErrorToast.show();
      throw new Error(res.statusText);
    }
    return res.json();
  }

  static async putData(path, data, headers = {}) {
    const res = await fetch(this.baseUrl + path, {
      method: 'PUT',
      // Extend standard headers with custom headers
      headers: {...Requests.headers, ...headers},
      body: JSON.stringify(data),
    });
    if (!res.ok) {
      ErrorToast.show();
      throw new Error(res.statusText);
    }
    return res.json();
  }
}

export default Requests;
