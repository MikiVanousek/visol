class Timestamp {
  constructor(value1, value2, value3, value4, value5, value6) {
    if (value1 === undefined) {
      value1 = new Date();
    }

    if (typeof value1 === 'number') {
      if (value2 === undefined || value3 === undefined) {
        throw new Error('Invalid timestamp values, need at least 3');
      } else {
        this._year = value1;
        this._month = value2;
        this._day = value3;
        this._hours = value4 || 0;
        this._minutes = value5 || 0;
        this._seconds = value6 || 0;
      }
    } else if (typeof value1 === 'string') {
      const numbers = value1.split(/(?<!^)[-:TZ]/)
          .filter((s) => s.length > 0).map(Number);
      if (numbers.length < 3 || numbers.length > 6) {
        throw new Error('Invalid timestamp string');
      }
      this._year = numbers[0];
      this._month = numbers[1];
      this._day = numbers[2];
      this._hours = numbers[3] || 0;
      this._minutes = numbers[4] || 0;
      this._seconds = numbers[5] || 0;
    } else if (value1 instanceof Date) {
      this._year = value1.getUTCFullYear();
      this._month = value1.getUTCMonth() + 1;
      this._day = value1.getUTCDate();
      this._hours = value1.getUTCHours();
      this._minutes = value1.getUTCMinutes();
      this._seconds = value1.getUTCSeconds();
    } else {
      throw new Error('Invalid timestamp argument');
    }

    // Note that in PostgreSQL, 24:00:00 is considered to be 00:00:00.
    if (this._month < 1 || this._month > 12 || this._day < 1 ||
        this._day > new Date(this._year, this._month, -1).getDate() ||
        this._hours < 0 || this._hours > 23 ||
        this._minutes < 0 || this._minutes > 59 ||
        this._seconds < 0 || this._seconds > 59) {
      throw new Error('Invalid timestamp values');
    }
  }

  get year() {
    return this._year;
  }

  get month() {
    return this._month;
  }

  get day() {
    return this._day;
  }

  get hours() {
    return this._hours;
  }

  get minutes() {
    return this._minutes;
  }

  get seconds() {
    return this._seconds;
  }

  formatted(format = 'YYYY-MM-DDThh:mm:ssZ') {
    let result = format;
    result = result.replace('YYYY', this.year.toString());
    result = result.replace('MM', this.month.toString().padStart(2, '0'));
    result = result.replace('M', this.month.toString());
    result = result.replace('DD', this.day.toString().padStart(2, '0'));
    result = result.replace('D', this.day.toString());
    result = result.replace('hh', this.hours.toString().padStart(2, '0'));
    result = result.replace('h', this.hours.toString());
    result = result.replace('mm', this.minutes.toString().padStart(2, '0'));
    result = result.replace('m', this.minutes.toString());
    result = result.replace('ss', this.seconds.toString().padStart(2, '0'));
    result = result.replace('s', this.seconds.toString());
    return result;
  }

  toDate() {
    return new Date(Date.UTC(
        this.year,
        this.month - 1,
        this.day,
        this.hours,
        this.minutes,
        this.seconds,
    ));
  }

  toLocal() {
    // Subtract the timezone offset
    const date = this.toDate();
    const offset = new Date().getTimezoneOffset() * 60 * 1000;
    return new Timestamp(new Date(date.getTime() - offset));
  }

  toUTC() {
    // Add the timezone offset
    const date = this.toDate();
    const offset = new Date().getTimezoneOffset() * 60 * 1000;
    return new Timestamp(new Date(date.getTime() + offset));
  }
}

export default Timestamp;
