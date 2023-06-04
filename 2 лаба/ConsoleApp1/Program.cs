using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace InformationTheory
{
    class Program
    {
        static void Main(string[] args)
        {
            
            string latinAlphabet = "abcdefghijklmnopqrstuvwxyz";
            string cyrillicAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
            string text = File.ReadAllText("text.txt");

            // Расчет частоты появления символов в тексте на основе латинского алфавита
            Dictionary<char, int> latinFrequency = new Dictionary<char, int>();
            foreach (char c in latinAlphabet)
            {
                int count = text.Count(x => x == c);
                latinFrequency.Add(c, count);
            }

            // Расчет частоты появления символов в тексте на основе кириллического алфавита
            Dictionary<char, int> cyrillicFrequency = new Dictionary<char, int>();
            foreach (char c in cyrillicAlphabet)
            {
                int count = text.Count(x => x == c);
                cyrillicFrequency.Add(c, count);
            }

            // Расчет общего количества символов в тексте
            int totalChars = text.Length;

            // Расчет вероятности появления каждого символа в тексте на основе латинского алфавита
            Dictionary<char, double> latinProbabilities = new Dictionary<char, double>();
            foreach (KeyValuePair<char, int> pair in latinFrequency)
            {
                double probability = (double)pair.Value / totalChars;
                latinProbabilities.Add(pair.Key, probability);
            }

            // Расчет вероятности появления каждого символа в тексте на основе кириллического алфавита
            Dictionary<char, double> cyrillicProbabilities = new Dictionary<char, double>();
            foreach (KeyValuePair<char, int> pair in cyrillicFrequency)
            {
                double probability = (double)pair.Value / totalChars;
                cyrillicProbabilities.Add(pair.Key, probability);
            }

            // Расчет энтропии латинского алфавита
            double latinEntropy = 0;
            foreach (double p in latinProbabilities.Values)
            {
                if (p > 0)
                {
                    latinEntropy += -p * Math.Log(p, 2);
                }
            }

            // Расчет энтропии кириллического алфавита
            double cyrillicEntropy = 0;
            foreach (double p in cyrillicProbabilities.Values)
            {
                if (p > 0)
                {
                    cyrillicEntropy += -p * Math.Log(p, 2);
                }
            }

            // Вывод результатов
            Console.WriteLine("\n=======================================\n");
            Console.WriteLine("\nЛАТИНИЦА\n");
            Console.WriteLine("Расчет частоты появления символов в тексте на основе латинского алфавита:");
            foreach (KeyValuePair<char, int> pair in latinFrequency)
            {
                Console.WriteLine(pair.Key + ": " + pair.Value);
            }

            Console.WriteLine("Общее количество символов в тексте: " + totalChars);

            Console.WriteLine("Расчет вероятности появления каждого символа в тексте на основе латинского алфавита:");
            foreach (KeyValuePair<char, double> pair in latinProbabilities)
            {
                Console.WriteLine(pair.Key + ": " + pair.Value);
            }
            Console.WriteLine("\n=======================================\n");
            Console.WriteLine("\nКИРИЛИЦА\n");
            Console.WriteLine("Расчет частоты появления символов в тексте на основе кириллического алфавита:");
            foreach (KeyValuePair<char, int> pair in cyrillicFrequency)
            {
                Console.WriteLine(pair.Key + ": " + pair.Value);
            }
            
            Console.WriteLine("Общее количество символов в тексте: " + totalChars);

            Console.WriteLine("Расчет вероятности появления каждого символа в тексте на основе кириллического алфавита:");
            foreach (KeyValuePair<char, double> pair in cyrillicProbabilities)
            {
                Console.WriteLine(pair.Key + ": " + pair.Value);
            }

            Console.WriteLine("Энтропия на основе латинского алфавита: " + latinEntropy);
            Console.WriteLine("Энтропия на основе алфавита кириллицы: " + cyrillicEntropy);

        }
    }
}
