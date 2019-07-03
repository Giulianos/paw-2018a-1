export default (minLength, msg) => pass => (pass && pass.length >= minLength)
  || msg;
