import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.polstat.laporinsarpras.api.ApiService
import com.polstat.laporinsarpras.request.LoginRequest
import com.polstat.laporinsarpras.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val apiService: ApiService) : ViewModel() {
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.login(LoginRequest(email, password))
                _loginResponse.value = response
            } catch (e: Exception) {
                // Gantilah dengan penanganan kesalahan yang sesuai
                _loginResponse.value = LoginResponse(null, "${e.message}", null)
            }
        }
    }

}