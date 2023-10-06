
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.ikapurwanti.foodappbinarchallenge.R
import com.ikapurwanti.foodappbinarchallenge.databinding.ActivityCheckoutBinding
import com.ikapurwanti.foodappbinarchallenge.databinding.LayoutDialogCheckoutSuccessBinding
import com.ikapurwanti.foodappbinarchallenge.presentation.home.HomeFragment

class CheckoutActivity : AppCompatActivity() {

    private val binding : ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClicklistener()
    }

    private fun setClicklistener() {
        binding.clActionOrder.setOnClickListener {
            showSuccessDialog()
        }
    }

    private fun showSuccessDialog() {
        val dialogBinding = LayoutDialogCheckoutSuccessBinding.inflate(layoutInflater)

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.tvBackToHome.setOnClickListener {
            startActivity(Intent(this, HomeFragment::class.java))
        }
        dialog.show()

    }
}