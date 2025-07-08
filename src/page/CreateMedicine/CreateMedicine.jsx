import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Checkbox } from "@/components/ui/checkbox";
import { useDispatch } from "react-redux";
import { createMedicine } from "@/State/Medicine/Action";

const CreateMedicine = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");
  const [medicine, setMedicine] = useState({
    brand: "",
    description: "",
    quantity: 0,
    suitableSkinTypes: [],
    price: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setMedicine((prev) => ({ ...prev, [name]: value }));
  };

  const handleSkinTypeChange = (type, checked) => {
    setMedicine((prev) => ({
      ...prev,
      suitableSkinTypes: checked
        ? [...prev.suitableSkinTypes, type]
        : prev.suitableSkinTypes.filter((t) => t !== type),
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(createMedicine({medicineData:medicine, jwt}))
    navigate("/medicines");
  };

  const skinTypes = ["OILY", "DRY", "COMBINATION", "SENSITIVE", "NORMAL"];

  return (
    <div className="p-5 lg:px-20 max-w-4xl mx-auto">
      <h1 className="font-bold text-3xl pb-5">Create New Medicine</h1>

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium mb-1">Brand</label>
          <Input
            name="brand"
            value={medicine.brand}
            onChange={handleChange}
            required
            placeholder="Enter medicine brand"
          />
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Description</label>
          <Input
            name="description"
            value={medicine.description}
            onChange={handleChange}
            required
            placeholder="Enter description"
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-1">Quantity</label>
            <Input
              type="number"
              name="quantity"
              value={medicine.quantity}
              onChange={handleChange}
              required
              min="0"
              placeholder="Enter quantity"
            />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1">Price</label>
            <Input
              name="price"
              value={medicine.price}
              onChange={handleChange}
              required
              placeholder="Enter price"
            />
          </div>
        </div>

        <div>
          <label className="block text-sm font-medium mb-1">Skin Types</label>
          <div className="grid grid-cols-2 gap-2">
            {skinTypes.map((type) => (
              <label key={type} className="flex items-center space-x-2">
                <Checkbox
                  checked={medicine.suitableSkinTypes.includes(type)}
                  onCheckedChange={(checked) =>
                    handleSkinTypeChange(type, checked)
                  }
                />
                <span>{type}</span>
              </label>
            ))}
          </div>
        </div>

        <div className="flex justify-end space-x-2 pt-4">
          <Button
            type="button"
            variant="outline"
            onClick={() => navigate("/medicines")}
          >
            Cancel
          </Button>
          <Button type="submit">Create Medicine</Button>
        </div>
      </form>
    </div>
  );
};

export default CreateMedicine;
