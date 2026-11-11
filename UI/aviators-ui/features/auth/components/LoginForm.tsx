import { LoginRequestDTO, loginUser } from "@/features/auth";
import { InputField } from "@/components/ui";
import Link from "next/link";
import { useSearchParams, useRouter } from "next/navigation";
import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useAppDispatch, useAppSelector } from "@/lib/store/hooks";

export function LoginForm() {
  const dispatch = useAppDispatch();
  const router = useRouter();
  const searchParams = useSearchParams();

  const redirectUrl = searchParams.get("redirect") || "/";

  const { isAuthenticated, status, error } = useAppSelector(
    (state) => state.auth,
  );

  useEffect(() => {
    if (isAuthenticated) {
      router.push(redirectUrl);
    }
  }, [isAuthenticated, redirectUrl, router]);

  const onSubmit = async (loginData: LoginRequestDTO) => {
    await dispatch(loginUser(loginData));
  };

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginRequestDTO>({
    mode: "onChange",
    defaultValues: {
      username: "",
      password: "",
    },
  });

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
          <div>
            <InputField
              name="username"
              placeholder="Username..."
              register={register}
              aria-label="username"
              error={errors.username}
              rules={{
                required: "Username is Required",
                pattern: {
                  value: /^[a-z0-9]{8,}$/,
                  message:
                    "Username should be lowercase and no whitespaces, minimum 8 characters",
                },
              }}
            />
          </div>

          <div>
            <InputField
              name="password"
              type="password"
              placeholder="Password..."
              aria-label="password"
              register={register}
              rules={{
                required: "Password is required",
                pattern: {
                  value:
                    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/,
                  message:
                    "Min 8 characters 1 uppercase, 1 lowercase, 1 number Any special character allowed",
                },
              }}
            />
          </div>

          <button
            type="submit"
            disabled={status === "loading"}
            className=" w-full  bg-indigo-600 hover:bg-indigo-500 disabled:opacity-50 text-white font-bold py-2 rounded-lg text-sm transition"
          >
            {status === "loading" ? "Signing In..." : "Sign In"}
          </button>
        </form>
        <Link href="/signup">
          <p className="text-xs text-slate-100 text-center mb-6 hover:text-blue-400">
            Register Admin? Click here
          </p>
        </Link>
      </div>
    </div>
  );
}
