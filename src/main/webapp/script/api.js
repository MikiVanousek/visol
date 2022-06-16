import Requests from "./requests.js";

class VisolApi {
  static portId = 1

  static getTerminals() {
    return Requests.getData(`/ports/${VisolApi.portId}/terminals`);
  }

  static postVessel(vessel) {
    return Requests.postData('/vessels', vessel)
  }
}

export default VisolApi
