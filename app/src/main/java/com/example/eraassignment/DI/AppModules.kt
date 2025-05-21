import com.example.eraassignment.viewModel.SearchScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AppModules {
    companion object {
        val appModule = module {
            viewModel { SearchScreenViewModel() }
        }
    }
}