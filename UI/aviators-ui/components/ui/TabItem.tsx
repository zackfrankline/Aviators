export function TabItem({
  onClick,
  isActive,
  title,
  closeTab,
}: {
  readonly onClick: () => void;
  readonly isActive: boolean;
  readonly title: string;
  readonly closeTab: (e: React.MouseEvent) => void;
}) {
  return (
    <div
      className={`group flex justify-between  items-center px-4 py-2 cursor-pointer rounded-t-lg border-t border-x text-sm transition-all min-w-35 max-w-50
        ${
          isActive
            ? "bg-slate-200 border-slate-700 text-slate-900"
            : "bg-transparent border-transparent text-slate-700 hover:bg-slate-200 hover:text-slate-900"
        }`}
    >
      <button onClick={onClick}>
        <span className="truncate flex-1 mr-3 font-medium">{title}</span>
      </button>
      <button
        onClick={closeTab}
        className="w-5 h-5 flex items-center justify-center rounded hover:bg-slate-700 transition-colors text-slate-400 hover:text-red-400" 
          //  ${isActive ? "text-slate-400 hover:text-red-400" : "opacity-50 group-hover:opacity-100"}`}
      >
        ✕
      </button>
    </div>
  );
}
