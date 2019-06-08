const numRe = /.*[0-9]+.*/i;

export default pass => pass && pass.length >= 8 && pass.toUpperCase() !== pass && pass.toLowerCase() !== pass &&  numRe.test(pass);