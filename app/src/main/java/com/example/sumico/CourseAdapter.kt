import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sumico.CourseInfo
import com.example.sumico.R
import com.squareup.picasso.Picasso

class CourseAdapter(private val courseList: List<CourseInfo>) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.courseImage)
        val titleView: TextView = itemView.findViewById(R.id.courseTitle)
        val descView: TextView = itemView.findViewById(R.id.courseDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courseList[position]
        holder.titleView.text = course.courseName
        holder.descView.text = course.text
        Picasso.get().load(course.imageURL).into(holder.imageView)
    }

    override fun getItemCount() = courseList.size
}
