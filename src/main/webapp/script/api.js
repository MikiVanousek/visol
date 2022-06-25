import Requests from './requests.js';

class VisolApi {
  static portId = 1;
  static get;

  static getTerminals = () =>
    Requests.getData(`/ports/${VisolApi.portId}/terminals`);

  static getBerthsPerTerminal = (terminalId) =>
    Requests.getData(`/terminals/${terminalId}/berths`);

  static getSchedulesPerTerminal = (terminalId) =>
    Requests.getData(`/terminals/${terminalId}/schedules`);

  static getVesselsPerTerminal = (terminalId) =>
    Requests.getData(`/terminals/${terminalId}/vessels`);

  static postVessel = (vessel, reason) =>
    Requests.postData('/vessels', vessel, {'Reason': reason});

  static putVessel = (vesselId, vessel, reason) =>
    Requests.putData(`/vessels/${vesselId}`, vessel, {'Reason': reason});

  static putSchedule = (vesselId, schedule, reason) =>
    Requests.putData(`/vessels/${vesselId}/schedule`, schedule, {'Reason': reason});
}

export default VisolApi;
