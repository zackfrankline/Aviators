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
            ? "bg-secondary border-border text-card-foreground"
            : "bg-transparent border-transparent text-secondary-foreground hover:bg-secondary hover:text-foreground"
        }`}
    >
      <button onClick={onClick}>
        <span className="truncate flex-1 mr-3 font-medium">{title}</span>
      </button>
      <button
        onClick={closeTab}
        className="w-5 h-5 flex items-center justify-center rounded hover:bg-secondary transition-colors text-secondary-foreground hover:text-red-400"
      >
        ✕
      </button>
    </div>
  );
}
