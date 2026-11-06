"use client";
import { useState } from "react";
import {
  Tab,
  useWorkspace,
  WorkspaceContextType,
} from "@/context/WorkspaceContext";
import TabItem from "./TabItem";
import { FaFolderPlus } from "react-icons/fa";
import { useSelector } from "react-redux";
import { RootState } from "@/app/lib/redux/store";
import { Category } from "@/app/lib/definitions";

enum ArticleStatus {
  Open,
  Working,
  Published,
  Draft,
}

type CreateArticleRequestDTO = {
  title: string;
  summary: string;
  content: string;
  status: ArticleStatus;
  categoryId: string;
  bannerUrl: string;
  youtubeLink: string;
};

export default function Workspace() {
  const { tabs, activeTabId, openTab, closeTab, setActiveTabId } =
    useWorkspace() as WorkspaceContextType;

  const { categories } = useSelector((state: RootState) => state.content);

  // Local state for the editor form
  const [editorData, setEditorData] = useState<CreateArticleRequestDTO>({
    title: "",
    summary: "",
    content: "",
    status: ArticleStatus.Draft,
    categoryId: "",
    bannerUrl: "",
    youtubeLink: "",
  });

  const handleInputChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >,
  ) => {
    const { name, value } = e.target;
    setEditorData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSave = (e: React.MouseEvent) => {
    e.preventDefault();
    console.log("Saving article data:", editorData);
    // In a real app, you would make an API call to your Content Service here
  };

  const handleCreateCategory = () => {
    openTab({
      title: "Create New Category",
      type: "CREATE",
      subType:'CATEGORY',
    })
  }

  if (tabs.length === 0) {
    return (
      <div className="flex-1 flex flex-col items-center justify-center text-slate-500">
        <span className="text-4xl mb-4">🛸</span>
        <p>Your workspace is empty.</p>
        <p className="text-sm">
          Select an item from the sidebar to open a tab.
        </p>
      </div>
    );
  }

  return (
    <div className="h-full flex flex-col relative ">
      {/* Tab Header Row */}
      <div className="flex  overflow-x-auto border-b border-slate-800 shrink-0 scrollbar-hide px-2 pt-2 gap-1">
        {tabs.map((tab: Tab) => {
          const isActive = activeTabId === tab.id;
          return (
            <TabItem
              key={tab.id}
              onClick={() => setActiveTabId?.(tab.id)}
              isActive={isActive}
              title={tab.title}
              closeTab={(e: React.MouseEvent) => closeTab(tab.id, e)}
            />
          );
        })}
      </div>

      {/* Main Form/Read Area */}
      <div className="flex-1 overflow-y-auto p-8 relative">
        {tabs.map((tab: Tab) => (
          <div
            key={tab.id}
            className={`h-full max-w-4xl mx-auto ${activeTabId === tab.id ? "block" : "hidden"}`}
          >
            {/* VIEW: CREATE/EDIT ARTICLE */}
            {(tab.type === "CREATE" || tab.type === "EDIT") && (
              <div className="animate-in fade-in slide-in-from-bottom-4 duration-500">
                <div className="flex justify-between items-center mb-8 border-b border-slate-800 pb-4">
                  <h2 className="text-2xl font-bold text-slate-600 tracking-wide">
                    {tab.type === "CREATE"
                      ? "Draft New Article"
                      : "Edit Article"}
                  </h2>
                  <button
                    onClick={handleSave}
                    className="bg-indigo-600 hover:bg-indigo-500 text-white px-6 py-2 rounded-md shadow-[0_0_15px_rgba(79,70,229,0.2)] hover:shadow-[0_0_20px_rgba(79,70,229,0.4)] transition-all font-medium text-sm tracking-wide"
                  >
                    Save to Database
                  </button>
                </div>

                <form className="space-y-6">
                  {/* Top Row: Title, Status, Category */}
                  <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    {/* Title Input */}
                    <div className="space-y-2 md:col-span-2">
                      <p className="text-xs font-bold text-slate-400 uppercase tracking-wider">
                        Title
                      </p>
                      <input
                        type="text"
                        name="title"
                        value={editorData.title}
                        onChange={handleInputChange}
                        placeholder="e.g. Next-Gen Aviation Systems"
                        className="w-full bg-slate-950 border border-slate-700 rounded-md px-4 py-3 text-slate-100 focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 transition-all placeholder:text-slate-600"
                      />
                    </div>

                    {/* Status Select */}
                    <div className="space-y-2">
                      <p className="text-xs font-bold text-slate-400 uppercase tracking-wider">
                        Status
                      </p>
                      <select
                        name="status"
                        value={editorData.status}
                        onChange={handleInputChange}
                        className="w-full bg-slate-950 border border-slate-700 rounded-md px-4 py-3 text-slate-100 focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 appearance-none cursor-pointer transition-all"
                      >
                        <option value="Draft">Draft</option>
                        <option value="Working">In Progress</option>
                        <option value="Published">Publish</option>
                      </select>
                    </div>
                  </div>

                  {/* Second Row: Slug & Category */}
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    {/* Slug (Auto-generated/Disabled) */}
                    <div className="space-y-2">
                      <p className="text-xs font-bold text-slate-400 uppercase tracking-wider flex justify-between">
                        Slug{" "}
                        <span className="text-[10px] text-slate-600">
                          Auto-generated
                        </span>
                      </p>
                      <input
                        type="text"
                        name="slug"
                        value={
                          editorData.title
                            .toLowerCase()
                            .replace(/ /g, "-")
                            .replace(/[^\w-]+/g, "") || "auto-generated-slug"
                        }
                        readOnly
                        className="w-full bg-slate-900 border border-slate-800 rounded-md px-4 py-3 text-slate-500 cursor-not-allowed font-mono text-sm"
                      />
                    </div>

                    {/* Category Select */}
                    <div className="space-y-2">
                      <p className="text-xs font-bold text-slate-400 uppercase tracking-wider">
                        Category
                      </p>
                      <div className="flex flex-row gap-2 items-center">
                        <select
                          name="category"
                          value={editorData.categoryId}
                          onChange={handleInputChange}
                          className="w-full bg-slate-950 border border-slate-700 rounded-md px-4 py-3 text-slate-100 focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 appearance-none cursor-pointer transition-all"
                        >
                          <option value="" disabled>
                            Select a category...
                          </option>
                          {categories.map((category: Category) => (
                            <option
                              key={category.id}
                              value={category.name}
                              id={category.id}
                            >
                              {category.name}
                            </option>
                          ))}
                          {/*
                          <option value="hypersonic">
                            🚀 Hypersonic Flight
                          </option>
                          <option value="supersonic">
                            ✈️ Supersonic Travel
                          </option>
                          <option value="aerodynamics">🌪️ Aerodynamics</option>
                          <option value="propulsion">
                            🔥 Propulsion Systems
                          </option> */}
                        </select>
                        {/* Create category Icon */}
                        <div>
                          <FaFolderPlus onClick={handleCreateCategory} size={36} />
                        </div>
                      </div>
                    </div>
                  </div>

                  {/* Summary Textarea */}
                  <div className="space-y-2 pt-2">
                    <p className="text-xs font-bold text-slate-400 uppercase tracking-wider flex justify-between">
                      Summary
                      <span
                        className={`text-[10px] ${editorData.summary.length > 200 ? "text-red-400" : "text-slate-500"}`}
                      >
                        {editorData.summary.length} / 200
                      </span>
                    </p>
                    <textarea
                      name="summary"
                      value={editorData.summary}
                      onChange={handleInputChange}
                      placeholder="Brief overview of the article content..."
                      className="w-full h-24 bg-slate-950 border border-slate-700 rounded-md px-4 py-3 text-slate-100 focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 resize-none transition-all placeholder:text-slate-600 leading-relaxed"
                    />
                  </div>

                  {/* Full Content Textarea */}
                  <div className="space-y-2 pt-2 h-full flex flex-col">
                    <p className="text-xs font-bold text-slate-400 uppercase tracking-wider">
                      Full Content
                    </p>
                    <textarea
                      name="content"
                      value={editorData.content}
                      onChange={handleInputChange}
                      placeholder="Begin typing article content here..."
                      className="w-full h-96 bg-slate-950 border border-slate-700 rounded-md px-4 py-4 text-slate-200 focus:outline-none focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500 resize-y font-mono text-sm leading-relaxed transition-all placeholder:text-slate-700"
                    />
                  </div>
                </form>
              </div>
            )}

            {/* VIEW: ARTICLE LISTS (Placeholder logic) */}
            {tab.type === "LIST" && (
              <div className="animate-in fade-in duration-300">
                <h2 className="text-2xl font-bold mb-6 capitalize text-slate-100 tracking-wide">
                  {/* {tab.payload.filterValue.toLowerCase()} Articles */}
                </h2>
                {/* ... Card List Implementation ... */}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}
