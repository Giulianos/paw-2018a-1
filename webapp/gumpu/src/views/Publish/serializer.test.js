import serialize from './serializer';

const sampleFormValues = {
  description: "CocaCola",
  unitPrice: "12",
  quantity: "10",
  detailedDescription: "Gaseosa CocaCola, botella de 1.5L por unidad",
  tags: "cocacola, coca, cola, gaseosa"
};

describe('New publication serializer', () => {
  it('should convert tags to array of strings and price/quantity to number', () => {
    const expectedSerializedPublication = {
      description: "CocaCola",
      unitPrice: 12.0,
      quantity: 10,
      detailedDescription: "Gaseosa CocaCola, botella de 1.5L por unidad",
      tags: ["cocacola", "coca", "cola", "gaseosa"]
    };

    expect(serialize(sampleFormValues)).toEqual(expectedSerializedPublication);
  });

  it('should remove repeated tags', () => {
    const expectedSerializedPublication = {
      description: "CocaCola",
      unitPrice: 12.0,
      quantity: 10,
      detailedDescription: "Gaseosa CocaCola, botella de 1.5L por unidad",
      tags: ["cocacola", "coca", "cola", "gaseosa"]
    };

    const sampleFormValuesWithRepeatedTags = {...sampleFormValues, tags: "cocacola, coca, cola, gaseosa, coca, gaseosa"};

    expect(serialize(sampleFormValuesWithRepeatedTags)).toEqual(expectedSerializedPublication);
  });
});
