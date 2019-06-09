export default fullName =>
  (!!fullName && fullName.length >= 3 && fullName.length <= 30) || 'Name should have between 3 and 30 characters';