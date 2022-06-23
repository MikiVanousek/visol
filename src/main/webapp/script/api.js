import Requests from "./requests.js";

class VisolApi {
  static portId = 1

  static getTerminals = () => Requests.getData(`/ports/${VisolApi.portId}/terminals`);

  static postVessel = (vessel) => Requests.postData('/vessels', vessel)

  static putSchedule = (vessel_id, schedule) => Requests.putData(`/vessels/${vessel_id}/schedule`, schedule)

  static formatLocalDateTime = (dt) => new Date(Date.parse(dt)).toISOString().substring(0,19) + 'Z'
}

export default VisolApi
