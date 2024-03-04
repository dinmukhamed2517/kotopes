package kz.sd.kotopes.fragments

import kz.sd.kotopes.base.BaseFragment
import kz.sd.kotopes.databinding.FragmentPaymentBinding

class PaymentFragment:BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {
    override fun onBindView() {
        super.onBindView()

        binding.sendBtn.setOnClickListener {
            showCustomDialog("Успешно", "Вы пожертвовали "+binding.etMoney.text+" KZT")
        }
    }
}