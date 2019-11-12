package buu.informatics.s59160581.trafficsign


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import buu.informatics.s59160581.trafficsign.databinding.FragmentGameBinding
import androidx.appcompat.app.AppCompatActivity

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {
    data class Question(
        val text: String,
        val answers: List<String>)

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (Or better yet, don't define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "ใบขับขี่ต่อล่วงหน้าได้หรือไม่?",
            answers = listOf("ต่อล่วงหน้าได้ 3 เดือน ก่อนหมดอายุ", "ต่อล่วงหน้าได้ 8 เดือน ก่อนหมดอายุ", "ต่อล่วงหน้าได้ 1 ปี ก่อนหมดอายุ")),
        Question(text = "ใบขับขี่ชั่วคราว มีอายุกี่ปี?",
            answers = listOf("2 ปี", "5 ปี", "3 ปี")),
        Question(text = "การโอนรถ ต้องแจ้งต่อนายทะเบียนภายในกี่วัน?",
            answers = listOf("15 วัน", "7 วัน", "30 วัน"))
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

            val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

            // Shuffles the questions and sets the question index to the first question.
            randomizeQuestions()

            // Bind this fragment class to the layout
            binding.game = this

            // Set the onClickListener for the submitButton
            binding.submitBtn.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
            { view: View ->


                val checkedId = binding.questionRadioGroup.checkedRadioButtonId
                // Do nothing if nothing is checked (id == -1)
                if (-1 != checkedId) {
                    var answerIndex = 0
                    when (checkedId) {
                        R.id.secondAnswerRadioButton -> answerIndex = 1
                        R.id.thirdAnswerRadioButton -> answerIndex = 2
                    }
                    // The first answer in the original question is always the correct one, so if our
                    // answer matches, we have the correct answer.
                    if (answers[answerIndex] == currentQuestion.answers[0]) {
                        questionIndex++
                        // Advance to the next question
                        if (questionIndex < numQuestions) {
                            currentQuestion = questions[questionIndex]
                            setQuestion()
                            binding.invalidateAll()
                        } else {
                            // We've won!  Navigate to the gameWonFragment.
                            view.findNavController()
                                .navigate(R.id.action_gameFragment_to_winFragment)
                        }
                    } else {
                        // Game over! A wrong answer sends us to the gameOverFragment.
                        view.findNavController()
                            .navigate(R.id.action_gameFragment_to_loseFragment)
                    }
                }
            }

            setHasOptionsMenu(true)
            return binding.root
        }

        // randomize the questions and set the first question
        private fun randomizeQuestions() {
            questions.shuffle()
            questionIndex = 0
            setQuestion()
        }

        // Sets the question and randomizes the answers.  This only changes the data, not the UI.
        // Calling invalidateAll on the FragmentGameBinding updates the data.
        private fun setQuestion() {
            currentQuestion = questions[questionIndex]
            // randomize the answers into a copy of the array
            answers = currentQuestion.answers.toMutableList()
            // and shuffle them
            answers.shuffle()
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_traffic_sign, questionIndex + 1, numQuestions)
        }
}
