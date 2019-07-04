import uniq from 'lodash/uniq';

export default values => ({
  description: values.description,
  unitPrice: Number(values.unitPrice),
  quantity: Number(values.quantity),
  detailedDescription: values.detailedDescription,
  tags: uniq(values.tags.split(",").map(v => v.trim()))
});