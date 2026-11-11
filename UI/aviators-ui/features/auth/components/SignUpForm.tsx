import { registerAdminUser, AdminRegisterRequest  } from "@/features/auth";
import { InputField } from "@/components/ui";
import { useAppDispatch, useAppSelector } from "@/lib/store/hooks";
import Link from "next/link";
import { useRouter, useSearchParams } from "next/navigation";
import { useEffect } from "react";
import { useForm } from "react-hook-form";

export function SignUpForm() {
  const dispatch = useAppDispatch();
  const router = useRouter();
  const searchParams = useSearchParams();

  const redirectUrl = searchParams.get("redirect") || "/";

  const { isAuthenticated, error } = useAppSelector(
    (state) => state.auth,
  );

  useEffect(() => {
    if (isAuthenticated) {
      router.push(redirectUrl);
    }
  }, [isAuthenticated, redirectUrl, router]);

  const onSubmit = async (data: AdminRegisterRequest) => {
    await dispatch(
      registerAdminUser({
        name: data.name,
        userName: data.userName,
        passwordHash: data.passwordHash,
        email: data.email,
        secretKey: data.secretKey,
      }),
    );
  };

  const {
    register,
    handleSubmit,
    getValues,
    formState: { errors },
  } = useForm<AdminRegisterRequest>({
    mode: "onChange",
    defaultValues: {
      name: "",
      userName: "",
      email: "",
      passwordHash: "",
      confirmPasswordHash: "",
      secretKey: "",
    },
  });

  console.log("React Hook Form Error:", errors);

  return (
    <div className="min-h-screen flex items-center justify-center bg-slate-950 px-4">
      <div className="w-full max-w-md bg-slate-900 border border-slate-800 p-8 rounded-2xl shadow-xl">
        <h1 className="text-2xl font-bold text-slate-100 mb-2 text-center">
          Sign In
        </h1>
        <p className="text-xs text-slate-400 text-center mb-6">
          Access subscriber-only content and workspace
        </p>

        {error && (
          <div className="mb-4 p-3 bg-rose-500/10 border border-rose-500/30 text-rose-400 text-xs rounded-lg">
            {error}
          </div>
        )}

        <form
          onSubmit={handleSubmit(onSubmit)}
          className="flex flex-col gap-4 mb-2"
        >
          <InputField
            name="name"
            register={register}
            rules={{
              required: "Full name is required",
              minLength: { value: 2, message: "Min length is 2" },
            }}
            error={errors.name}
            placeholder="Name..."
          />

          <InputField
            name="userName"
            register={register}
            placeholder="Username..."
            rules={{
              required: "Username is Required",
              pattern: {
                value: /^[a-z0-9]{8,}$/,
                message:
                  "Username should be lowercase, minimum 8 characters and no whitespaces",
              },
            }}
            error={errors.userName}
          />

          <InputField
            name="email"
            placeholder="Email..."
            register={register}
            rules={{
              required: "Email is required",
              pattern: {
                value: /^[^\s@]+@[^\s@.]+\.[^\s@.]+$/,
                message: "Invalid email",
              },
            }}
            error={errors.email}
          />

          <InputField
            name="passwordHash"
            type="password"
            placeholder="Password..."
            register={register}
            rules={{
              required: "Password is required",
              pattern: {
                value:
                  /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/,
                message:
                  "Min 8 characters 1 uppercase, 1 lowercase, 1 number Any special character allowed",
              },
              deps: ["confirmPasswordHash"],
            }}
            error={errors.passwordHash}
          />

          <InputField
            name="confirmPasswordHash"
            type="password"
            placeholder="Confirm Password..."
            register={register}
            rules={{
              required: "Confirm password is required",
              pattern: {
                value:
                  /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/,
                message:
                  "Min 8 characters 1 uppercase, 1 lowercase, 1 number Any special character allowed",
              },
              validate: (value) =>
                value === getValues("passwordHash") || "Passwords don't match",
            }}
            error={errors.confirmPasswordHash}
          />

          <InputField
            name="secretKey"
            type="password"
            placeholder="Admin Secret (Optional)..."
            register={register}
            error={errors.secretKey}
          />

          <button
            type="submit"
            className="mt-2 w-full bg-indigo-600 hover:bg-indigo-500 disabled:opacity-50 text-white font-medium py-2 rounded-lg text-sm transition"
          >
            Submit
          </button>
        </form>

        <Link href="/login">
          <p className="text-xs text-slate-100 text-center mb-6 hover:text-blue-400">
            Already have an account? Login
          </p>
        </Link>
      </div>
    </div>
  );
}
