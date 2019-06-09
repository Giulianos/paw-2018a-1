export default pass =>
  (pass && pass.length >= 8)
  || 'Password should have at least 8 characters';