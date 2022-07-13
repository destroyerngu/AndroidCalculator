package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mTvResult: TextView
    lateinit var mBtnAdd: TextView
    lateinit var mBtnSubtract: Button
    lateinit var mBtnMultiply: Button
    lateinit var mBtnDivide: Button
    lateinit var mBtnCalculate: Button
    lateinit var mBtnOne: Button
    lateinit var mBtnTwo: Button
    lateinit var mBtnThree: Button
    lateinit var mBtnFour: Button
    lateinit var mBtnFive: Button
    lateinit var mBtnSix: Button
    lateinit var mBtnSeven: Button
    lateinit var mBtnEight: Button
    lateinit var mBtnNine: Button
    lateinit var mBtnZero: Button
    lateinit var mBtnCln: Button
    var operateCount: Int = 0
    var numCount: Int = 0
    var mixList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 进行控件绑定
        init()
        // 设置点击事件
        setClickEvent()
        // 首次进入，重置一次程序
        cln()
    }

    fun setClickEvent() {
        mTvResult.setOnClickListener(this)
        mBtnAdd.setOnClickListener(this)
        mBtnSubtract.setOnClickListener(this)
        mBtnMultiply.setOnClickListener(this)
        mBtnDivide.setOnClickListener(this)
        mBtnCalculate.setOnClickListener(this)
        mBtnOne.setOnClickListener(this)
        mBtnTwo.setOnClickListener(this)
        mBtnThree.setOnClickListener(this)
        mBtnFour.setOnClickListener(this)
        mBtnFive.setOnClickListener(this)
        mBtnSix.setOnClickListener(this)
        mBtnSeven.setOnClickListener(this)
        mBtnEight.setOnClickListener(this)
        mBtnNine.setOnClickListener(this)
    }
    /**
     * 点击事件的具体代码
     * */
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button_0 -> addNum(0)
            R.id.button_1 -> addNum(1)
            R.id.button_2 -> addNum(2)
            R.id.button_3 -> addNum(3)
            R.id.button_4 -> addNum(4)
            R.id.button_5 -> addNum(5)
            R.id.button_6 -> addNum(6)
            R.id.button_7 -> addNum(7)
            R.id.button_8 -> addNum(8)
            R.id.button_9 -> addNum(9)
            R.id.buttonEqual -> equal()
            R.id.buttonAdd -> addOperate('+')
            R.id.buttonMinus -> addOperate('-')
            R.id.buttonMultiply -> addOperate('*')
            R.id.buttonDivide -> addOperate('/')
            R.id.buttonClear -> cln()
        }
    }



    /**
     * 对控件进行初始化
     * */
    fun init() {
        mTvResult = findViewById<TextView>(R.id.tvResult)
        mBtnAdd = findViewById<Button>(R.id.buttonAdd)
        mBtnSubtract = findViewById<Button>(R.id.buttonMinus)
        mBtnMultiply = findViewById<Button>(R.id.buttonMultiply)
        mBtnDivide = findViewById<Button>(R.id.buttonDivide)
        mBtnCalculate = findViewById<Button>(R.id.buttonEqual)
        mBtnOne = findViewById<Button>(R.id.button_1)
        mBtnTwo = findViewById<Button>(R.id.button_2)
        mBtnThree = findViewById<Button>(R.id.button_3)
        mBtnFour = findViewById<Button>(R.id.button_4)
        mBtnFive = findViewById<Button>(R.id.button_5)
        mBtnSix = findViewById<Button>(R.id.button_6)
        mBtnSeven = findViewById<Button>(R.id.button_7)
        mBtnEight = findViewById<Button>(R.id.button_8)
        mBtnNine = findViewById<Button>(R.id.button_9)
        mBtnZero = findViewById<Button>(R.id.button_0)
        mBtnCln = findViewById<Button>(R.id.buttonClear)
    }

    /**
     * 构建表达式的String形式并展示
     * */
    fun show(list: ArrayList<String>) {
        if (list.size == 0) mTvResult?.setText("0")
        var sb = StringBuilder()
        for (item in list) {
            sb.append(item)
        }
        mTvResult?.setText(sb)
    }

    /**
     * 点击数字时调用此函数
     * */
    fun addNum(num: Long) {
        if (operateCount == numCount) {
            // 当前为1位的数字
            numCount++
            mixList.add(num.toString())
        } else {
            // 当前为多位的数字
            val numPlus: Long = mixList.get(mixList.size - 1).toLong() * 10 + num
            mixList.set(mixList.size - 1, numPlus.toString())
        }
        // 实时展示界面变化
        show(mixList)
    }

    /**
     * 点击运算符时调用此函数
     * */
    fun addOperate(operate: Char) {
        if (numCount == 0) return
        if (operateCount < numCount) {
            operateCount++
            mixList.add(operate.toString())
        } else if (operateCount == numCount) {
            mixList.set(mixList.size - 1, operate.toString())
        }
        show(mixList)
    }

    /**
     * 点击“=”后调用此函数
     * */
    fun equal() {
        // 当前只输入数字，不需要计算，直接返回
        if (operateCount == 0) return
        // 当前最后一位为运算符，不合法，对最后一位运算符进行删除
        if (operateCount == numCount) {
            mixList.removeAt(mixList.size - 1)
        }
        // 计算表达式的值
        calculate()
        // 进行界面展示
        show(mixList)
    }

    /**
     * 具体的计算函数
     * */
    fun calculate() {
        // 优先进行乘除运算
        for (i in mixList.indices) {
            // 兜底判断，防止后续的删除操作导致List长度减小，导致下标溢出
            if (i > mixList.size - 1) break;
            if (mixList.get(i).equals("*")) {
                // 计算“乘”
                val tmp: Long = mixList.get(i - 1).toLong() * mixList.get(i + 1).toLong()
                // 更新值并删除相关操作数
                mixList.set(i - 1, tmp.toString())
                mixList.removeAt(i)
                mixList.removeAt(i)
            } else if (mixList.get(i).equals("/")) {
                // 对非法输入“除0”进行判断
                if (mixList.get(i + 1).equals("0")) break;
                val tmp: Long = mixList.get(i - 1).toLong() / mixList.get(i + 1).toLong()
                mixList.set(i - 1, tmp.toString())
                mixList.removeAt(i)
                mixList.removeAt(i)
            }
        }

        // 进行加减运算
        for(i in mixList.indices) {
            // 兜底判断，防止后续的删除操作导致List长度减小，导致下标溢出
            if (i > mixList.size - 1) break;
            if (mixList.get(i).equals("+")) {
                val tmp: Long = mixList.get(i - 1).toLong() + mixList.get(i + 1).toLong()
                mixList.set(i - 1, tmp.toString())
                mixList.removeAt(i)
                mixList.removeAt(i)
            } else if (mixList.get(i).equals("-")) {
                val tmp: Long = mixList.get(i - 1).toLong() - mixList.get(i + 1).toLong()
                mixList.set(i - 1, tmp.toString())
                mixList.removeAt(i)
                mixList.removeAt(i)
            }
        }
    }

    /**
     * 重置操作
     * 即为 清空操作
     * */
    fun cln() {
        operateCount = 0
        numCount = 0
        mixList.clear()
        mTvResult.setText("0")
    }




}