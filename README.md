### SimpleItemDecoration
RecyclerView 常用 DividerItemDecoration。可以直接下载文件直接使用，查看效果。

使用 `drawable` 作为绘制内容，支持 `LinearLayout` `GridLayout` 布局

#### 功能说明：
- 默认不显示首尾的分割线，但是线性布局时可以使用特定 `ItemDecoration` 子类显示最后一条或者第一条分割线。
- 支持设置每个分割线的 margin 属性。margin 是相对于 child 而言，并非是指 `RecyclerView`。
- 可以保证 Item 的大小的一致，`ItemDecoration` 不会占用 Item 的空间
