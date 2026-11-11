// app/components/InputField.tsx
import React from "react";
import {
  UseFormRegister,
  FieldError,
  RegisterOptions,
  FieldValues,
  Path,
} from "react-hook-form";

type InputFieldProps<T extends FieldValues> = Readonly<
  Omit<React.InputHTMLAttributes<HTMLInputElement>, "name"> & {
    name: Path<T>;
    register: UseFormRegister<T>;
    rules?: RegisterOptions<T, Path<T>>;
    error?: FieldError;
  }
>;

export function InputField<T extends FieldValues>({
  name,
  register,
  rules,
  error,
  type = "text",
  ...rest
}: InputFieldProps<T>) {
  const inputStyles =
    "w-full bg-slate-800 border border-slate-700 rounded-lg px-3 py-2 text-sm text-slate-100 focus:outline-none focus:border-indigo-500";
  const errorStyles = "px-1 text-slate-100 text-sm";

  return (
    // <div className="w-full">
    <>
      <input
        id={name}
        type={type}
        className={inputStyles}
        {...register(name, rules)}
        {...rest}
      />
      {error?.message && <p className={errorStyles}>{error.message}</p>}
    </>
    // {/* </div> */}
  );
}
