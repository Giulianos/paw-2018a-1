export default (max, msg) => v => Number(v)>0 && Number(v) <= max && Math.ceil(v) === Number(v) || msg;