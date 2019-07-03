export default (min, max, msg) => fullName => (!!fullName && fullName.length >= min && fullName.length <= max) || msg;
