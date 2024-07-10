import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.techtriv_fyp.Models.Users
import com.example.techtriv_fyp.databinding.UserHolderBinding

class UserAdapter (var context: android.content.Context, var userlist: ArrayList<Users>):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    inner  class ViewHolder(var binding: UserHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = UserHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name.setText(userlist.get(position).Name)

    }
}