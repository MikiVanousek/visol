class Requets {
  static baseUrl = 'https://private-anon-10598d6953-visol.apiary-mock.com';

  static async postData(path = '', data = {}) {
    const res = await fetch(this.baseUrl + path, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    });
  return res.json();
}
}

export default Requets;
