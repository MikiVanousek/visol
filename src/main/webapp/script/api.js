import Requests from "./requests.js";

class VisolApi {
  static portId = 1;

  static getTerminals = () =>
    Requests.getData(`/ports/${VisolApi.portId}/terminals`);

  static getBerthsPerTerminal = (terminalId) =>
    Requests.getData(`/terminals/${terminalId}/berths`);

  static getSchedulesPerTerminal = (terminalId) =>
    Requests.getData(`/terminals/${terminalId}/schedules`);

  static getVesselsPerTerminal = (terminalId) =>
    Requests.getData(`/terminals/${terminalId}/vessels`);

  static postVessel = (vessel) =>
    Requests.postData('/vessels', vessel);

  static putSchedule = (vesselId, schedule) =>
    Requests.putData(`/vessels/${vesselId}/schedule`, schedule);

  static formatLocalDateTime = (dt) =>
    new Date(Date.parse(dt)).toISOString().substring(0, 19) + 'Z';

  static get;
}

export default VisolApi;
