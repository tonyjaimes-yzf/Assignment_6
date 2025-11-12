GITHUB REPO: https://github.com/tonyjaimes-yzf/Assignment_6.git

Passing Status Badge:
[![Playwright Tests](https://github.com/tonyjaimes-yzf/Assignment_6/actions/workflows/playwright.yml/badge.svg)](https://github.com/tonyjaimes-yzf/Assignment_6/actions/workflows/playwright.yml)

Manual UI Testing vs AI-assisted UI Testing
-------------------
- I started this assignment using the manual UI testing method. We had the playwright tool to be able to open up a browser and interact with the website. This tool allowed us to record and log every single action performed on the website in Java code and Junit tests.


- I found the playwright tool to be VERY simple and easy to use. At the top of the playwright browser, there were several asserting tools at our disposal. The one that I used the most was the tool that assertThat a specific text was displayed on the screen. Given that our assignment instructed us to assert that specific messages and texts were displayed, for example asserting that "your cart is empty", this tool was extremely helpful. When I selected it, It allowed me to hover over a spedific text. I would click it and it would automatically write the assert test case for me in runnable java. This saved me so much time compared to if I would have had to write all the tests from scratch. Once I got the hang of the tool, It took me no more than 2-3 minutes to perform successful runs and log Java code and Junit test cases using the playwright tool.


- Overall, I found the manual UI testing to be very easy to do. The playwright tool was very helpful in writing the Java code and Junit tests.


- For the MCP for Playwright LLM Agent part, I struggled very much. Firstly, It took me a very long time to set up the server and get it connected to the ai agent on Vs Code. For some reason, it kept failing to connect to the MCP server. This went on for a couple hours, but eventually I got the MCP server to connect to the LLM agent. The most difficult part was giving the LLM specific enough language prompts that it could use to accurately navigate and interact with the website. Giving the LLM prompts like "type earbuds into the search bar top right" did not work in my case and the LLM would not interact with the website the way the assignment intended it to. 


- This whole process took hours, but I eventually got the LLM to fully navigate the website based on the assignments instructions from searching for earbuds to ensuring the cart is empty. The biggest issue was with the code that the LLM agent generated. Put into short words, it simply would not work and it kept failing the test. The Java code would correctly open up the playwright browser, but the generated code would not interact with the website at all. I spent hours trying to guide the LLM agent to fix the code so it would interact with the website as expected. It was trial and error. Little by little, the LLM would fix one test case after the other. Eventually, the LLM successfully fixed all of its generated code and I was able to produde a successful video of the run. It should be noted that this whole process took hours for me.

-------------------

Final Thoughts

- Overall, I can see the use for both Manual and LLM UI Testing.
- Though i can see the usefullnes of an LLM agent assisting in generating code and testing a UI like the one in this assignment, I would prefer the manual UI route.
- The main reason for this is that with the manual UI testing, I had a lot more control on where I wanted to go and what I wanted to do. The playwright tools for asserting test cases made it very simple to generate java code and Junit tests
- With the LLM agent, it felt a lot more unpredictable. Not only was it difficult to get it started, the LLM would generate code and Junit tests that were simply incorrect. I had to speend extra time guiding the LLM to fix the code and generate logically correct tests.
- For these reasons, I would stick with the manual UI testing method using playwright tools.